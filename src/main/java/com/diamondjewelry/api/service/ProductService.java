package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Product;
import com.diamondjewelry.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private MongoTemplate template;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return repository.findById(id);
    }

    public List<Product> getProductBySearchTitleKeyword(String titleKeyword) {
        return repository.findByTitleIgnoreCaseLike(titleKeyword);
    }

    public List<Product> getProduct(Map<String, String> params, String sortMode, int limit) {
        Query query = new Query();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getKey().equals("group")) {
                    query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
                }
                else {
                    query.addCriteria(Criteria.where("details." + entry.getKey()).is(entry.getValue()));
                }
            }
        }
        switch (sortMode) {
            case "oldest":
                query.with(Sort.by(Sort.Direction.ASC, "_id"));
                break;
            case "latest":
                query.with(Sort.by(Sort.Direction.DESC, "_id"));
                break;
            case "mostPopular":
                query.with(Sort.by(Sort.Direction.DESC, "sold"));
                break;
            default:
                break;
        }
        if (limit != 0) {
            query.limit(limit);
        }
        return template.find(query, Product.class);
    }

    public void addProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
        repository.insert(product);
    }

    public void updateProduct(Product product) {
        repository.save(product);
    }

    public void removeProductById(String id) {
        repository.deleteById(id);
    }

    public List<String> getAllValuesOfFilter(String filter) {
        return template.query(Product.class)
                .distinct("details." + filter)
                .as(String.class)
                .all();
    }
}