package com.udacity.baking.ui.ingredients;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.data.entities.Step;
import com.udacity.baking.ui.recipes.RecipesActivity;
import com.udacity.baking.ui.steps.StepsActivity;
import com.udacity.baking.ui.steps.StepsFragment;

import org.parceler.Parcels;

public class IngredientsActivity extends AppCompatActivity implements IngredientsFragment.OnStepClickListener {

    public static final String StepPositionExtra = "step_position";
    public static final String RecipeExtra = "recipe";

    private static final String RecipeKey = "recipe";
    private static final String TwoPaneKey = "two_pane";

    private FragmentManager mFragmentManager;
    private Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            Intent intent = getIntent();

            Parcelable parcelable = intent.getParcelableExtra(RecipesActivity.RecipeExtra);
            mRecipe = Parcels.unwrap(parcelable);

            initIngredientsFragment();

            mTwoPane = findViewById(R.id.recipe_step_detail_linear_layout) != null;
        } else {
            if (savedInstanceState.containsKey(RecipeKey)) {
                Parcelable parcel = savedInstanceState.getParcelable(RecipeKey);
                mRecipe = Parcels.unwrap(parcel);
            }

            mTwoPane = savedInstanceState.getBoolean(TwoPaneKey);
        }
    }

    @Override
    public void onStepSelected(int position) {
        Step step = mRecipe.getSteps().get(position);

        if (mTwoPane) {
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setStep(step);

            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_steps, stepsFragment)
                    .commit();

            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(StepPositionExtra, position);
        bundle.putParcelable(RecipeExtra, Parcels.wrap(mRecipe));

        final Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipe != null) {
            outState.putParcelable(RecipeKey, Parcels.wrap(mRecipe));
        }

        outState.putBoolean(TwoPaneKey, mTwoPane);
    }

    private void initIngredientsFragment() {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipe(mRecipe);

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_ingredients, ingredientsFragment)
                .commit();
    }
}