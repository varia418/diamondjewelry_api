package com.diamondjewelry.api.repository;

import com.diamondjewelry.api.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByTitleIgnoreCaseLike(String titleKeyword);
}