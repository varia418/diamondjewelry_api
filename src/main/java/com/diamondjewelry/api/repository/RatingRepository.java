package com.diamondjewelry.api.repository;

import com.diamondjewelry.api.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.bson.Document;
import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Document> findByProductId(String productId);
}
