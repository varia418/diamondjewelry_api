package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Subscriber;
import com.diamondjewelry.api.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {
    @Autowired
    private SubscriberService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Subscriber>> getAllSubscriber() {
        return new ResponseEntity<>(service.getAllSubscribers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getSubscriberById(@PathVariable("id") String id) {
        if (!service.getSubscriberById(id).isPresent()) {
            return new ResponseEntity<>("Subscriber is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getSubscriberById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Subscriber> createNewSubscriber(@RequestBody Subscriber subscriber) {
        service.addSubscriber(subscriber);
        return new ResponseEntity<>(subscriber, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) {
        if (!service.getSubscriberById(subscriber.getId()).isPresent()) {
            service.addSubscriber(subscriber);
            return new ResponseEntity<>(subscriber, HttpStatus.CREATED);
        }
        service.updateSubscriber(subscriber);
        return new ResponseEntity<>(subscriber, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteSubscriberById(@PathVariable("id") String id) {
        Optional<Subscriber> subscriber = service.getSubscriberById(id);
        if (!subscriber.isPresent()) {
            return new ResponseEntity<>("Subscriber is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeSubscriberById(id);
        return new ResponseEntity<>(subscriber.get(), HttpStatus.OK);
    }
}
