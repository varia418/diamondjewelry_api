package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Cart;
import com.diamondjewelry.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> getAllCart() {
        return new ResponseEntity<>(service.getAllCarts(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getCartById(@PathVariable("id") String id) {
        if (!service.getCartById(id).isPresent()) {
            return new ResponseEntity<>("Cart is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getCartById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cart) {
        service.addCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
        if (!service.getCartById(cart.getId()).isPresent()) {
            service.addCart(cart);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        }
        service.updateCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable("id") String id) {
        Optional<Cart> cart = service.getCartById(id);
        if (!cart.isPresent()) {
            return new ResponseEntity<>("Cart is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeCartById(id);
        return new ResponseEntity<>(cart.get(), HttpStatus.OK);
    }
}
