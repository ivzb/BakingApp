package com.udacity.baking.utils;

import com.udacity.baking.data.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesUtils {

    public static List<String> getTitles(List<Recipe> recipes) {
        List<String> titles = new ArrayList<>();

        for (Recipe recipe: recipes) {
            titles.add(recipe.getName());
        }

        return titles;
    }
}
