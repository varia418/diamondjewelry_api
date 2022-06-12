package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Cart;
import com.diamondjewelry.api.model.CartItem;
import com.diamondjewelry.api.repository.CartRepository;
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
public class CartService {
    @Autowired
    private CartRepository repository;

    @Autowired
    private MongoTemplate template;

    public List<Cart> getAllCarts() {
        return repository.findAll();
    }

    public Optional<Cart> getCartById(String id) {
        return repository.findById(id);
    }

    public Optional<Cart> getCartByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public void addCart(Cart cart) {
        repository.insert(cart);
    }

    public void updateCart(Cart cart) {
        repository.save(cart);
    }

    public void removeCartById(String id) {
        repository.deleteById(id);
    }

    public void addItemToCart(String userId, CartItem item) {
        Optional<Cart> optionalCart = repository.findByUserId(userId);
        Cart cart;
        cart = optionalCart.orElseGet(() -> new Cart(null, userId, new ArrayList<>()));
        List<CartItem> items = cart.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        if (items.stream().anyMatch(cartItem -> cartItem.getId().equals(item.getId())))
        {
            items.forEach(cartItem -> {
                if (cartItem.getId().equals(item.getId())) {
                    cartItem.setQuantity(item.getQuantity());
                }
            });
        }
        else {
            items.add(item);
        }
        cart.setItems(items);
        repository.save(cart);
    }

    public void removeItemFromCart(String userId, String productId) {
        Optional<Cart> optionalCart = repository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> items = cart.getItems();
            items.removeIf(item -> item.getId().equals(productId));
            cart.setItems(items);
            repository.save(cart);
        }
    }

    public void removeAllItemsFromCart(String userId) {
        Optional<Cart> optionalCart = repository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setItems(new ArrayList<>());
            repository.save(cart);
        }
    }

    public List<?> getCartProductsByUserId(String userId) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("products")
                .localField("items.product_id")
                .foreignField("_id")
                .as("cart_products");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("user_id").is(new ObjectId(userId))), lookupOperation);
        Document resultDoc = (Document)template.aggregate(aggregation, "carts", Object.class).getRawResults().get("results", List.class).get(0);
        List<Document> cartProductsDoc = resultDoc.getList("cart_products", Document.class);
        List<CartItem> cartItems = repository.findByUserId(userId).get().getItems();
        cartItems.forEach(cartItem -> {
             cartProductsDoc.forEach(cartProductDoc -> {
                 if (cartProductDoc.get("_id", ObjectId.class).toHexString().equals(cartItem.getId())) {
                     cartProductDoc.append("quantity", cartItem.getQuantity());
                 }
             });
        });
        cartProductsDoc.forEach(cartProductDoc -> {
            cartProductDoc.append("id", cartProductDoc.get("_id", ObjectId.class).toHexString());
            cartProductDoc.remove("_id");
        });
        return cartProductsDoc.stream().map(cartProductDoc -> (Object)cartProductDoc).collect(Collectors.toList());
    }
}
