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
import com.udacity.baking.utils.AdapterUtils;

import org.parceler.Parcels;

public class IngredientsFragment extends Fragment {

    private final static String RecipeKey = "recipe";

    private OnStepClickListener mCallback;
    private Recipe mRecipe;

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
        if (savedInstanceState != null && savedInstanceState.containsKey(RecipeKey)) {
            Parcelable parcelable = savedInstanceState.getParcelable(RecipeKey);
            mRecipe = Parcels.unwrap(parcelable);
        }

        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        if (mRecipe != null) {
            initIngredients(rootView);
            initSteps(rootView);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        if (mRecipe != null) {
            currentState.putParcelable(RecipeKey, Parcels.wrap(mRecipe));
        }
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    private void initIngredients(View rootView) {
        RecyclerView rvIngredients = rootView.findViewById(R.id.rvIngredients);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        RecyclerView.LayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext());
        AdapterUtils.setAdapter(rvIngredients, ingredientsAdapter, ingredientsLayoutManager);
    }

    private void initSteps(View rootView) {
        RecyclerView rvSteps = rootView.findViewById(R.id.rvSteps);
        StepsAdapter stepsAdapter = new StepsAdapter(mRecipe.getSteps(), mCallback);
        RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getContext());
        AdapterUtils.setAdapter(rvSteps, stepsAdapter, stepsLayoutManager);
    }
}