package com.udacity.baking.data;

import com.udacity.baking.data.entities.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}