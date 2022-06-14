package com.diamondjewelry.api.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    @Field("full_name")
    private String fullName;
    @Field("dob")
    private String dob;
    @Field("tel")
    private String tel;
    @Field("email")
    private String email;
    @Field("address")
    private String address;
    @Field("password")
    private String password;
    @Field("favorite_products")
    private List<ObjectId> favoriteProducts;
    @Field("role")
    private String role;
    @Field("provider")
    private String provider;

    @PersistenceConstructor
    public User(String id, String fullName, String dob, String tel, String email, String address, String password, List<ObjectId> favoriteProducts, String role, String provider) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.password = password;
        this.favoriteProducts = favoriteProducts;
        this.role = role;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ObjectId> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<ObjectId> favoriteProducts) {
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
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", password='" + password + '\'' +
                ", favoriteProducts=" + favoriteProducts +
                ", role='" + role + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
