package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Rating;
import com.diamondjewelry.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/rating")
public class RatingController {
    @Autowired
    private RatingService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Rating>> getAllRating() {
        return new ResponseEntity<>(service.getAllRatings(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getRatingById(@PathVariable("id") String id) {
        if (!service.getRatingById(id).isPresent()) {
            return new ResponseEntity<>("Rating is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getRatingById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/productId")
    public ResponseEntity<List<Rating>> getRatingByProductId(@RequestParam("productId") String productId) {
        return new ResponseEntity<>(service.getRatingsByProductId(productId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Rating> createNewRating(@RequestBody Rating rating) {
        service.addRating(rating);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Rating> updateRating(@RequestBody Rating rating) {
        if (!service.getRatingById(rating.getId()).isPresent()) {
            service.addRating(rating);
            return new ResponseEntity<>(rating, HttpStatus.CREATED);
        }
        service.updateRating(rating);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteRatingById(@PathVariable("id") String id) {
        Optional<Rating> rating = service.getRatingById(id);
        if (!rating.isPresent()) {
            return new ResponseEntity<>("Rating is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeRatingById(id);
        return new ResponseEntity<>(rating.get(), HttpStatus.OK);
    }
}
