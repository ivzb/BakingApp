package com.udacity.baking.ui.recipes;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.baking.R;
import com.udacity.baking.data.BakingAPI;
import com.udacity.baking.data.RESTClient;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.utils.ParcelableUtils;
import com.udacity.baking.utils.RecipesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.baking.utils.ViewUtils.hide;
import static com.udacity.baking.utils.ViewUtils.show;

public class RecipesFragment extends Fragment
        implements Callback<List<Recipe>>, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String RecipesKey = "recipes";

    private OnRecipeClickListener mCallback;
    private List<Recipe> mRecipes;

    private GridView mGvRecipes;
    private ProgressBar mPbLoading;
    private TextView mTvLoadFailed;
    private Button mBtnTryAgain;

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    public RecipesFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        mGvRecipes = rootView.findViewById(R.id.gvRecipes);
        mPbLoading = rootView.findViewById(R.id.pbLoading);
        mTvLoadFailed = rootView.findViewById(R.id.tvLoadFailed);
        mBtnTryAgain = rootView.findViewById(R.id.btnTryAgain);

        mGvRecipes.setOnItemClickListener(this);
        mBtnTryAgain.setOnClickListener(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(RecipesKey)) {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray(RecipesKey);
            List<Recipe> recipes = ParcelableUtils.fromArray(parcelables);
            setRecipes(recipes);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipes != null) {
            Parcelable[] recipes = ParcelableUtils.toArray(mRecipes);
            outState.putParcelableArray(RecipesKey, recipes);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mRecipes == null) loadRecipes();
    }

    @Override
    public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
        int statusCode = response.code();

        hide(mPbLoading);

        if (statusCode != 200) {
            show(mTvLoadFailed);
            show(mBtnTryAgain);
            return;
        }

        hide(mTvLoadFailed);
        hide(mBtnTryAgain);

        setRecipes(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        hide(mPbLoading);
        show(mTvLoadFailed);
        show(mBtnTryAgain);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Recipe selectedRecipe = mRecipes.get(position);
        mCallback.onRecipeSelected(selectedRecipe);
    }

    @Override
    public void onClick(View view) {
        loadRecipes();
    }

    private void loadRecipes() {
        BakingAPI api = RESTClient
                .getClient()
                .create(BakingAPI.class);

        show(mPbLoading);

        final Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(this);
    }

    private void setRecipes(List<Recipe> recipes) {
        List<String> titles = RecipesUtils.getTitles(recipes);
        initGridView(titles);
        mRecipes = recipes;
    }

    private void initGridView(List<String> titles) {
        RecipesAdapter mAdapter = new RecipesAdapter(getContext(), titles);
        mGvRecipes.setAdapter(mAdapter);
    }
}