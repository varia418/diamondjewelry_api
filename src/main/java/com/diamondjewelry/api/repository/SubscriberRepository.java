package com.diamondjewelry.api.repository;

import com.diamondjewelry.api.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
}
