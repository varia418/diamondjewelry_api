package com.diamondjewelry.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {
    @Field(name = "product_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field("quantity")
    private int quantity;

    @PersistenceConstructor
    public CartItem(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
