package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Product;
import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.repository.UserRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private MongoTemplate template;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return repository.findById(id);
    }

    public void addUser(User user) {
        if (user.getFavoriteProducts() == null) {
            user.setFavoriteProducts(new ArrayList<>());
        }
        repository.insert(user);
    }

    public void updateUser(User user) {
        if (user.getFavoriteProducts() == null) {
            user.setFavoriteProducts(repository.findById(user.getId()).get().getFavoriteProducts());
        }
        if (user.getProvider().equals("LOCAL")) {
            user.setPassword(repository.findById(user.getId()).get().getPassword());
        }
        repository.save(user);
    }

    public void removeUserById(String id) {
        repository.deleteById(id);
    }

    public List<?> getLikedProductsByUserId(String id) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("products")
                .localField("favorite_products")
                .foreignField("_id")
                .as("liked_products");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("_id").is(new ObjectId(id))), lookupOperation);
        List<Document> likedProductsDoc = ((Document)(template.aggregate(aggregation, "users", Object.class).getRawResults().get("results", List.class).get(0))).getList("liked_products", Document.class);
        likedProductsDoc.forEach(likeProductDoc -> {
            likeProductDoc.append("id", likeProductDoc.get("_id", ObjectId.class).toHexString());
            likeProductDoc.remove("_id");
        });
        return likedProductsDoc.stream().map(likedProductDoc -> (Object)likedProductDoc).collect(Collectors.toList());
    }

    public void addLikedProduct(String id, String productId) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<ObjectId> likedProductId = user.getFavoriteProducts();
            if (likedProductId == null) {
                likedProductId = new ArrayList<>();
            }
            likedProductId.add(new ObjectId(productId));
            user.setFavoriteProducts(likedProductId);
            repository.save(user);
        }
    }

    public void removeLikedProduct(String id, String productId) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<ObjectId> likedProductId = user.getFavoriteProducts();
            likedProductId.remove(new ObjectId(productId));
            user.setFavoriteProducts(likedProductId);
            repository.save(user);
        }
    }

    public void removeAllLikedProducts(String id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFavoriteProducts(new ArrayList<>());
            repository.save(user);
        }
    }

    public boolean existsLikedProduct(String id, String productId) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFavoriteProducts().contains(new ObjectId(productId));
        }
        return false;
    }

    public Optional<User> findUserByEmailAndRoleAndProvider(String email, String role, String provider) {
        return repository.findByEmailAndRoleAndProvider(email, role, provider);
    }

    public boolean existsUserByEmailAndRoleAndProvider(String email, String role, String provider) {
        return repository.existsByEmailAndRoleAndProvider(email, role, provider);
    }
}
