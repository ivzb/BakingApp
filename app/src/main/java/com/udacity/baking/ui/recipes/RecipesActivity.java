package com.udacity.baking.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.ui.ingredients.IngredientsActivity;

import org.parceler.Parcels;

public class RecipesActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener {

    public static final String RecipeExtra = "recipe";

    private static final int TabletGridColumns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        boolean isTablet = findViewById(R.id.tablet_mode) != null;
        GridView gridView = findViewById(R.id.gvRecipes);

        if (isTablet) {
            gridView.setNumColumns(TabletGridColumns);
        }
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