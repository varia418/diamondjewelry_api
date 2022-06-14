package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.CartItem;
import com.diamondjewelry.api.model.Order;
import com.diamondjewelry.api.model.User;
import com.diamondjewelry.api.repository.OrderRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private MongoTemplate template;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return repository.findById(id);
    }

    public void addOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
        order.setStatus("Đang xử lý");
        if (order.getAddress() == null) {
            order.setAddress(template.findById(new ObjectId(order.getUserId()), User.class).getAddress());
        }
        repository.insert(order);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public void updateOrder(Order order) {
        repository.save(order);
    }

    public void removeOrderById(String id) {
        repository.deleteById(id);
    }

    public void updateStatus(String id, String status) {
        Optional<Order> optionalOrder = repository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            repository.save(order);
        }
    }

    public List<?> getOrderProductsById(String id) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("products")
                .localField("items.product_id")
                .foreignField("_id")
                .as("order_products");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("_id").is(new ObjectId(id))), lookupOperation);
        Document resultDoc = (Document)template.aggregate(aggregation, "orders", Object.class).getRawResults().get("results", List.class).get(0);
        List<Document> orderProductsDoc = resultDoc.getList("order_products", Document.class);
        List<CartItem> orderItems = repository.findById(id).get().getItems();
        orderItems.forEach(orderItem -> {
            orderProductsDoc.forEach(orderProductDoc -> {
                if (orderProductDoc.get("_id", ObjectId.class).toHexString().equals(orderItem.getId())) {
                    orderProductDoc.append("quantity", orderItem.getQuantity());
                }
            });
        });
        return orderProductsDoc.stream().map(orderProductDoc -> (Object)orderProductDoc).collect(Collectors.toList());
    }
}
