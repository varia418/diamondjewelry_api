package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.payload.LoginDto;
import com.diamondjewelry.api.payload.LoginRes;
import com.diamondjewelry.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
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
        Optional<User> userOptional = service.findUserByEmailAndRoleAndProvider(loginDto.getEmail(), "USER", "LOCAL");
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(new LoginRes(user.getId(), getJWTToken(user)), HttpStatus.OK);
            }
            return new ResponseEntity<>("Sai mật khẩu", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Tài khoản không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin/oauth2")
    public ResponseEntity<?> authenticatedOauth2(@RequestBody User user) {
        Optional<User> userOptional = service.findUserByEmailAndRoleAndProvider(user.getEmail(), user.getRole(), user.getProvider());
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(new LoginRes(userOptional.get().getId(), getJWTToken(userOptional.get())), HttpStatus.OK);
        }
        service.addUser(user);
        return new ResponseEntity<>(new LoginRes(user.getId(), getJWTToken(user)), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin/admin")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = service.findUserByEmailAndRoleAndProvider(loginDto.getEmail(), "ADMIN", "LOCAL");
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(new LoginRes(user.getId(), getJWTToken(user)), HttpStatus.OK);
            }
            return new ResponseEntity<>("Sai mật khẩu!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Tài khoản không tồn tại!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (service.existsUserByEmailAndRoleAndProvider(user.getEmail(), user.getRole(), "LOCAL")) {
            return new ResponseEntity<>("Email này đã tồn tại trong hệ thống!", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        service.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
