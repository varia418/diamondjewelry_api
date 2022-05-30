package com.diamondjewelry.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Details {
    @Field("brand")
    private String brand;
    @Field("material")
    private String material;
    @Field("chain_material")
    private String chainMaterial;
    @Field("purity")
    private String purity;
    @Field("gender")
    private String gender;
    @Field("color")
    private String color;
    @Field("type")
    private String type;

    @PersistenceConstructor
    public Details(String brand, String material, String chainMaterial, String purity, String gender, String color, String type) {
        this.brand = brand;
        this.material = material;
        this.chainMaterial = chainMaterial;
        this.purity = purity;
        this.gender = gender;
        this.color = color;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getChainMaterial() {
        return chainMaterial;
    }

    public void setChainMaterial(String chainMaterial) {
        this.chainMaterial = chainMaterial;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Details{" +
                "brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", chainMaterial='" + chainMaterial + '\'' +
                ", purity='" + purity + '\'' +
                ", gender='" + gender + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
