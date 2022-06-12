package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Cart;
import com.diamondjewelry.api.model.CartItem;
import com.diamondjewelry.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/carts")
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

    @RequestMapping(method = RequestMethod.PUT, value = "/addItem/{userId}")
    public ResponseEntity<?> addItemToCart(@PathVariable("userId") String userId, @RequestBody CartItem item) {
        service.addItemToCart(userId, item);
        return new ResponseEntity<>("add item " + item.getId() + " successfully", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeItem/{userId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("userId") String userId, @RequestBody String productId) {
        Optional<Cart> cart = service.getCartByUserId(userId);
        if (!cart.isPresent()) {
            return new ResponseEntity<>("Cart is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeItemFromCart(userId, productId);
        return new ResponseEntity<>("remove item " + productId + " successfully", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAllItems/{userId}")
    public ResponseEntity<?> removeAllItemsFromCart(@PathVariable("userId") String userId) {
        Optional<Cart> cart = service.getCartByUserId(userId);
        if (!cart.isPresent()) {
            return new ResponseEntity<>("Cart is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeAllItemsFromCart(userId);
        return new ResponseEntity<>("remove all cart items of user " + userId + " successfully", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartItems/{userId}")
    public ResponseEntity<?> getLikedProductByUserId(@PathVariable("userId") String userId) {
        Optional<Cart> cart = service.getCartByUserId(userId);
        if (!cart.isPresent()) {
            return new ResponseEntity<>("Cart is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getCartProductsByUserId(userId), HttpStatus.OK);
    }
}
