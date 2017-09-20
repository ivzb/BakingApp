package com.udacity.baking.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.idlingResource.SimpleIdlingResource;
import com.udacity.baking.ui.base.IdlingActivity;
import com.udacity.baking.ui.ingredients.IngredientsActivity;
import com.udacity.baking.ui.ingredients.IngredientsFragment;

import org.parceler.Parcels;

public class RecipesActivity extends IdlingActivity implements RecipesFragment.OnRecipeClickListener {

    public static final String RecipeExtra = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        boolean isTablet = findViewById(R.id.tablet_mode) != null;

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipesFragment recipesFragment = new RecipesFragment();
        recipesFragment.setTablet(isTablet);
        recipesFragment.setIdlingResource(getIdlingResource());

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_recipes, recipesFragment)
                .commit();
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeExtra, Parcels.wrap(recipe));

        final Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}