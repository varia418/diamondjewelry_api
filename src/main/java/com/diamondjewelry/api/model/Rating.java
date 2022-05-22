package com.diamondjewelry.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("ratings")
public class Rating {
    @Id
    private String id;
    @Field("user_id")
    private String userId;
    @Field("product_id")
    private String productId;
    @Field("rating_star")
    private float ratingStar;
    @Field("content")
    private String content;

    public Rating(String userId, String productId, float ratingStar, String content) {
        this.userId = userId;
        this.productId = productId;
        this.ratingStar = ratingStar;
        this.content = content;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", ratingStar=" + ratingStar +
                ", content='" + content + '\'' +
                '}';
    }
}
