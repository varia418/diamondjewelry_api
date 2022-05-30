package com.diamondjewelry.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Document("orders")
public class Order {
    @Id
    private String id;
    @Field(name = "user_id", targetType = FieldType.OBJECT_ID)
    private String userId;
    @Field("items")
    @Reference
    private List<CartItem> items;
    @Field("address")
    private String address;
    @Field("VAT_fee")
    private double VATFee;
    @Field("total_cost")
    private double totalCost;
    @Field("created_at")
    private String createdAt;

    @PersistenceConstructor
    public Order(String id, String userId, List<CartItem> items, String address, double VATFee, double totalCost, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.VATFee = VATFee;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getVATFee() {
        return VATFee;
    }

    public void setVATFee(double VATFee) {
        this.VATFee = VATFee;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", VATFee=" + VATFee +
                ", totalCost=" + totalCost +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
