package com.udacity.baking.ui.ingredients;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.idlingResource.SimpleIdlingResource;
import com.udacity.baking.utils.AdapterUtils;
import com.udacity.baking.utils.ParcelableUtils;

import org.parceler.Parcels;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private final static String RecipeKey = "recipe";
    private static final String IngredientsLayoutManagerStateKey = "ingredients_layout_manager";
    private static final String StepsLayoutManagerStateKey = "steps_layout_manager";

    private OnStepClickListener mCallback;
    private Recipe mRecipe;

    private RecyclerView.LayoutManager mIngredientsLayoutManager;
    private RecyclerView.LayoutManager mStepsLayoutManager;

    private RecyclerView mRvIngredients;
    private RecyclerView mRvSteps;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnIngredientClickListener");
        }
    }

    public IngredientsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIngredientsLayoutManager = new LinearLayoutManager(getContext());
        mStepsLayoutManager = new LinearLayoutManager(getContext());

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RecipeKey)) {
                Parcelable parcelable = savedInstanceState.getParcelable(RecipeKey);
                mRecipe = Parcels.unwrap(parcelable);
            }

            if (savedInstanceState.containsKey(IngredientsLayoutManagerStateKey)) {
                Parcelable state = savedInstanceState.getParcelable(IngredientsLayoutManagerStateKey);
                mIngredientsLayoutManager.onRestoreInstanceState(state);
            }

            if (savedInstanceState.containsKey(StepsLayoutManagerStateKey)) {
                Parcelable state = savedInstanceState.getParcelable(StepsLayoutManagerStateKey);
                mStepsLayoutManager.onRestoreInstanceState(state);
            }
        }

        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        mRvIngredients = rootView.findViewById(R.id.rvIngredients);
        mRvSteps = rootView.findViewById(R.id.rvSteps);

        if (mRecipe != null) {
            initIngredients();
            initSteps();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        if (mRecipe != null) {
            currentState.putParcelable(RecipeKey, Parcels.wrap(mRecipe));
        }

        if (mIngredientsLayoutManager != null) {
            currentState.putParcelable(IngredientsLayoutManagerStateKey, mIngredientsLayoutManager.onSaveInstanceState());
        }

        if (mStepsLayoutManager != null) {
            currentState.putParcelable(StepsLayoutManagerStateKey, mStepsLayoutManager.onSaveInstanceState());
        }
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    private void initIngredients() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        AdapterUtils.setAdapter(mRvIngredients, ingredientsAdapter, mIngredientsLayoutManager);
    }

    private void initSteps() {
        StepsAdapter stepsAdapter = new StepsAdapter(mRecipe.getSteps(), mCallback);
        AdapterUtils.setAdapter(mRvSteps, stepsAdapter, mStepsLayoutManager);
    }
}