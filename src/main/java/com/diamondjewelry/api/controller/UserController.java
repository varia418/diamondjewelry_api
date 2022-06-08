package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        if (!service.getUserById(id).isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getUserById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        service.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        if (!service.getUserById(user.getId()).isPresent()) {
            service.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeUserById(id);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addAddress/{id}")
    public ResponseEntity<String> addAddress(@PathVariable("id") String id, @RequestBody String address) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.addAddress(id, address);
        return new ResponseEntity<>("add address " + address + " successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAddress/{id}")
    public ResponseEntity<String> removeAddress(@PathVariable("id") String id, @RequestBody int index) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeAddress(id, index);
        return new ResponseEntity<>("remove address successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/likedProducts/{id}")
    public ResponseEntity<?> getLikedProductByUserId(@PathVariable("id") String id) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getLikedProductsByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addLikedProduct/{id}")
    public ResponseEntity<?> addLikedProduct(@PathVariable("id") String id, @RequestBody String likedProduct) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.addLikedProduct(id, likedProduct);
        return new ResponseEntity<>("add product " + likedProduct + " to favorite successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeLikedProduct/{id}")
    public ResponseEntity<?> removeLikedProduct(@PathVariable("id") String id, @RequestBody String likedProduct) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeLikedProduct(id, likedProduct);
        return new ResponseEntity<>("remove product " + likedProduct + " from favorite successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAllLikedProduct/{id}")
    public ResponseEntity<?> removeAllLikedProduct(@PathVariable("id") String id) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("User is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeAllLikedProduct(id);
        return new ResponseEntity<>("remove all products from favorite successfully!", HttpStatus.OK);
    }
}
