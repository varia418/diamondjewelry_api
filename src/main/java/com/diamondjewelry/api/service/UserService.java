package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return repository.findById(id);
    }

    public void addUser(User user) {
        repository.insert(user);
    }

    public void updateUser(User user) {
        repository.save(user);
    }

    public void removeUserById(String id) {
        repository.deleteById(id);
    }
}
