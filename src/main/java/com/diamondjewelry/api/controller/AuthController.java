package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.payload.LoginDto;
import com.diamondjewelry.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin/user")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = service.findUserByEmailAndRole(loginDto.getEmail(), "USER");
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(getJWTToken(user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Wrong password.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User don't exists.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin/admin")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = service.findUserByEmailAndRole(loginDto.getEmail(), "ADMIN");
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(getJWTToken(user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Wrong password.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User don't exists.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (service.existsUserByEmailAndRole(user.getEmail(), user.getRole())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        service.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private String getJWTToken(User user) {
        String secretKey = "JWTSecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(user.getRole());

        return Jwts
                .builder()
                .setId("diamondJewelryJWT")
                .setSubject(user.getEmail())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
