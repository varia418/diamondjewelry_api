package com.diamondjewelry.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("products")
public class Product {
    @Id
    private String id;
    @Field("title")
    private String title;
    @Field("price")
    private double price;
    @Field("description")
    private String description;
    @Field("details")
    private Details details;
    @Field("image")
    private String image;
    @Field("stock")
    private int stock;
    @Field("sold")
    private int sold;
    @Field("created_at")
    private String createdAt;


    @PersistenceConstructor
    public Product(String title, double price, String description, Details details, String image, int stock, int sold) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.details = details;
        this.image = image;
        this.stock = stock;
        this.sold = sold;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", details=" + details +
                ", image='" + image + '\'' +
                ", stock=" + stock +
                ", sold=" + sold +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
