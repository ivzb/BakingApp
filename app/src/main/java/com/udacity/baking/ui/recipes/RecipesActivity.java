package com.udacity.baking.ui.recipes;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Ingredient;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.provider.BakingContract;
import com.udacity.baking.ui.base.IdlingActivity;
import com.udacity.baking.ui.ingredients.IngredientsActivity;
import com.udacity.baking.widget.WidgetService;

import org.parceler.Parcels;

public class RecipesActivity extends IdlingActivity implements RecipesFragment.OnRecipeClickListener {

    public static final String RecipeExtra = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        if (savedInstanceState == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            boolean isTablet = findViewById(R.id.tablet_mode) != null;

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipesFragment recipesFragment = new RecipesFragment();
            recipesFragment.setTablet(isTablet);
            recipesFragment.setIdlingResource(getIdlingResource());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_recipes, recipesFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeExtra, Parcels.wrap(recipe));

        final Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        getContentResolver().delete(BakingContract.IngredientEntry.CONTENT_URI, null, null);

        for (Ingredient ingredient: recipe.getIngredients()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BakingContract.IngredientEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
            getContentResolver().insert(BakingContract.IngredientEntry.CONTENT_URI, contentValues);
        }

        WidgetService.startActionUpdateRecipe(this);
    }
}