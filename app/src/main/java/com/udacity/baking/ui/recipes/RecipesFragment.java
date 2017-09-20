package com.udacity.baking.ui.recipes;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.baking.R;
import com.udacity.baking.data.BakingAPI;
import com.udacity.baking.data.RESTClient;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.idlingResource.SimpleIdlingResource;
import com.udacity.baking.utils.AdapterUtils;
import com.udacity.baking.utils.ParcelableUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.baking.utils.ViewUtils.hide;
import static com.udacity.baking.utils.ViewUtils.show;

public class RecipesFragment extends Fragment implements Callback<List<Recipe>>, View.OnClickListener {

    private static final String RecipesKey = "recipes";
    private static final String LayoutManagerStateKey = "layout_manager";

    private static final int PhoneGridSpanColumns = 1;
    private static final int TabletGridSpanColumns = 3;

    private OnRecipeClickListener mCallback;
    private List<Recipe> mRecipes;
    private boolean mIsTablet;
    private SimpleIdlingResource mIdlingResource;

    private GridLayoutManager mStepsLayoutManager;

    private RecyclerView mRvRecipes;
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

        mRvRecipes = rootView.findViewById(R.id.rvRecipes);
        mPbLoading = rootView.findViewById(R.id.pbLoading);
        mTvLoadFailed = rootView.findViewById(R.id.tvLoadFailed);
        mBtnTryAgain = rootView.findViewById(R.id.btnTryAgain);

        mBtnTryAgain.setOnClickListener(this);

        int spanCount = mIsTablet ? TabletGridSpanColumns : PhoneGridSpanColumns;
        mStepsLayoutManager = new GridLayoutManager(getContext(), spanCount);

        if (savedInstanceState != null && savedInstanceState.containsKey(RecipesKey)) {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray(RecipesKey);
            List<Recipe> recipes = ParcelableUtils.fromArray(parcelables);

            if (savedInstanceState.containsKey(LayoutManagerStateKey)) {
                Parcelable state = savedInstanceState.getParcelable(LayoutManagerStateKey);
                mStepsLayoutManager.onRestoreInstanceState(state);
            }

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

        if (mStepsLayoutManager != null) {
            outState.putParcelable(LayoutManagerStateKey, mStepsLayoutManager.onSaveInstanceState());
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

        if (mIdlingResource != null) mIdlingResource.setIdleState(true);
    }

    @Override
    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        hide(mPbLoading);
        show(mTvLoadFailed);
        show(mBtnTryAgain);

        if (mIdlingResource != null) mIdlingResource.setIdleState(true);
    }

    @Override
    public void onClick(View view) {
        loadRecipes();
    }

    public void setTablet(boolean isTablet) {
        mIsTablet = isTablet;
    }

    public void setIdlingResource(SimpleIdlingResource idlingResource) {
        mIdlingResource = idlingResource;
    }

    private void loadRecipes() {
        if (mIdlingResource != null) mIdlingResource.setIdleState(false);

        BakingAPI api = RESTClient
                .getClient()
                .create(BakingAPI.class);

        show(mPbLoading);

        final Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(this);
    }

    private void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        initRecipes();
    }

    private void initRecipes() {
        RecipesAdapter recipesAdapter = new RecipesAdapter(mRecipes, mCallback);
        AdapterUtils.setAdapter(mRvRecipes, recipesAdapter, mStepsLayoutManager);
    }
}