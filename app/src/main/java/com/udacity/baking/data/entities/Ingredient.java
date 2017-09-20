package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    @SerializedName("id")
    int id;

    @SerializedName("quantity")
    double quantity;

    @SerializedName("measure")
    String measure;

    @SerializedName("ingredient")
    String ingredient;

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