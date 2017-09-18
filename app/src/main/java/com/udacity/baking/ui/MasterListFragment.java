package com.udacity.baking.ui;

import android.content.Context;
import android.os.Bundle;
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
import com.udacity.baking.utils.RecipesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.baking.utils.ViewUtils.hide;
import static com.udacity.baking.utils.ViewUtils.show;

public class MasterListFragment extends Fragment
        implements Callback<List<Recipe>>, AdapterView.OnItemClickListener, View.OnClickListener {

    private OnRecipeClickListener mCallback;

    private GridView mGvRecipes;
    private ProgressBar mPbLoading;
    private TextView mTvLoadFailed;
    private Button mBtnTryAgain;

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    public MasterListFragment() { }

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

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        mGvRecipes = rootView.findViewById(R.id.gvRecipes);
        mPbLoading = rootView.findViewById(R.id.pbLoading);
        mTvLoadFailed = rootView.findViewById(R.id.tvLoadFailed);
        mBtnTryAgain = rootView.findViewById(R.id.btnTryAgain);

        mGvRecipes.setOnItemClickListener(this);
        mBtnTryAgain.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadRecipes();
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

        List<Recipe> recipes = response.body();
        List<String> titles = RecipesUtils.getTitles(recipes);
        initGridView(titles);
    }

    @Override
    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        hide(mPbLoading);
        show(mTvLoadFailed);
        show(mBtnTryAgain);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mCallback.onRecipeSelected(position);
    }

    @Override
    public void onClick(View view) {
        loadRecipes();
    }

    private void loadRecipes() {
        BakingAPI api = RESTClient
                .getClient()
                .create(BakingAPI.class);

        final Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(this);
    }

    private void initGridView(List<String> titles) {
        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), titles);
        mGvRecipes.setAdapter(mAdapter);
    }
}