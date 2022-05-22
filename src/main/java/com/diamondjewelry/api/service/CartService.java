package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Cart;
import com.diamondjewelry.api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    public List<Cart> getAllCarts() {
        return repository.findAll();
    }

    public Optional<Cart> getCartById(String id) {
        return repository.findById(id);
    }

    public void addCart(Cart cart) {
        repository.insert(cart);
    }

    public void updateCart(Cart cart) {
        repository.save(cart);
    }

    public void removeCartById(String id) {
        repository.deleteById(id);
    }
}
