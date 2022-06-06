package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Order;
import com.diamondjewelry.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") String id) {
        if (!service.getOrderById(id).isPresent()) {
            return new ResponseEntity<>("Order is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getOrderById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> createNewOrder(@RequestBody Order order) {
        service.addOrder(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        if (!service.getOrderById(order.getId()).isPresent()) {
            service.addOrder(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        service.updateOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("id") String id) {
        Optional<Order> order = service.getOrderById(id);
        if (!order.isPresent()) {
            return new ResponseEntity<>("Order is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeOrderById(id);
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }
}
