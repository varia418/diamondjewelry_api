package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

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
        service.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
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
}
