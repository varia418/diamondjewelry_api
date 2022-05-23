package com.diamondjewelry.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    @Field("full_name")
    private String fullName;
    @Field("dob")
    private String dob;
    @Field("cell")
    private String cell;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field(name = "favorite_products", targetType = FieldType.OBJECT_ID)
    private List<String> favoriteProducts;
    @Field("role")
    private String role;
    @Field("provider")
    private String provider;

    public User(String fullName, String dob, String cell, String email, String password, List<String> favoriteProducts, String provider) {
        this.fullName = fullName;
        this.dob = dob;
        this.cell = cell;
        this.email = email;
        this.password = password;
        this.favoriteProducts = favoriteProducts;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<String> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", cell='" + cell + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", favoriteProducts=" + favoriteProducts +
                ", role='" + role + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
