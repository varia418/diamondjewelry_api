package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Order;
import com.diamondjewelry.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return repository.findById(id);
    }

    public void addOrder(Order order) {
        repository.insert(order);
    }

    public void updateOrder(Order order) {
        repository.save(order);
    }

    public void removeOrderById(String id) {
        repository.deleteById(id);
    }
}
