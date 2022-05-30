package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Subscriber;
import com.diamondjewelry.api.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository repository;

    public List<Subscriber> getAllSubscribers() {
        return repository.findAll();
    }

    public Optional<Subscriber> getSubscriberById(String id) {
        return repository.findById(id);
    }

    public void addSubscriber(Subscriber subscriber) {
        repository.insert(subscriber);
    }

    public void updateSubscriber(Subscriber subscriber) {
        repository.save(subscriber);
    }

    public void removeSubscriberById(String id) {
        repository.deleteById(id);
    }
}
