package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Rating;
import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.repository.RatingRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    @Autowired
    private MongoTemplate template;

    public List<Rating> getAllRatings() {
        return repository.findAll();
    }

    public Optional<Rating> getRatingById(String id) {
        return repository.findById(id);
    }
    
    public List<?> getRatingsByProductId(String productId) {
        List<Document> ratingsDoc = repository.findByProductId(productId);
        System.out.println(ratingsDoc);
        ratingsDoc.forEach(ratingDoc -> {
            ObjectId userId = ratingDoc.get("user_id", ObjectId.class);
            ratingDoc.append("userName", template.findById(userId, User.class).getFullName());

            ratingDoc.append("id", ratingDoc.get("_id", ObjectId.class).toHexString());
            ratingDoc.append("userId", ratingDoc.get("user_id", ObjectId.class).toHexString());
            ratingDoc.append("productId", ratingDoc.get("product_id", ObjectId.class).toHexString());
            ratingDoc.append("ratingStar", ratingDoc.get("rating_star", Double.class));

            ratingDoc.remove("_id");
            ratingDoc.remove("user_id");
            ratingDoc.remove("product_id");
            ratingDoc.remove("rating_star");
        });
        return ratingsDoc.stream().map(ratingDoc -> (Object)ratingDoc).collect(Collectors.toList());
    }

    public void addRating(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
        repository.insert(rating);
    }

    public void updateRating(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
        repository.save(rating);
    }

    public void removeRatingById(String id) {
        repository.deleteById(id);
    }
}
