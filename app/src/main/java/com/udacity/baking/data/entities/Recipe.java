package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Recipe {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("ingredients")
    List<Ingredient> ingredients;

    @SerializedName("steps")
    List<Step> steps;

    @SerializedName("serving")
    int serving;

    @SerializedName("image")
    String image;

    Recipe() { }

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int serving, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.serving = serving;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServing() {
        return serving;
    }

    public String getImage() {
        return image;
    }
}