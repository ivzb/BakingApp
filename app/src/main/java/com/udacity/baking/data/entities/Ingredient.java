package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    @SerializedName("id")
    private int id;

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    public Ingredient() { }

    public Ingredient(int id, double quantity, String measure, String ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}