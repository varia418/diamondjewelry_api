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
    @Field("major_type")
    private String majorType;
    @Field("minor_type")
    private String minorType;

    @PersistenceConstructor
    public Details(String brand, String material, String chainMaterial, String purity, String gender, String color, String majorType, String minorType) {
        this.brand = brand;
        this.material = material;
        this.chainMaterial = chainMaterial;
        this.purity = purity;
        this.gender = gender;
        this.color = color;
        this.majorType = majorType;
        this.minorType = minorType;
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

    public String getAgeOfGold() {
        return purity;
    }

    public void setAgeOfGold(String purity) {
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

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMinorType() {
        return minorType;
    }

    public void setMinorType(String minorType) {
        this.minorType = minorType;
    }

    @Override
    public String toString() {
        return "Details{" +
                "brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", chainMaterial='" + chainMaterial + '\'' +
                ", gender='" + gender + '\'' +
                ", color='" + color + '\'' +
                ", majorType='" + majorType + '\'' +
                ", minorType='" + minorType + '\'' +
                '}';
    }
}
