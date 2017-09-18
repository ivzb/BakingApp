package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("id")
    private int id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;
}