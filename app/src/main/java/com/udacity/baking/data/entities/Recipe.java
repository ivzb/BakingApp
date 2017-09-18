package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    private List<Step> steps;

    @SerializedName("serving")
    private int serving;

    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }
}