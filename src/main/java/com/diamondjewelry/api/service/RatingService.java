package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Rating;
import com.diamondjewelry.api.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    public List<Rating> getAllRatings() {
        return repository.findAll();
    }

    public Optional<Rating> getRatingById(String id) {
        return repository.findById(id);
    }
    
    public List<Rating> getRatingsByProductId(String productId) {
        return repository.findByProductId(productId);
    }

    public void addRating(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
        repository.insert(rating);
    }

    public void updateRating(Rating rating) {
        repository.save(rating);
    }

    public void removeRatingById(String id) {
        repository.deleteById(id);
    }
}
