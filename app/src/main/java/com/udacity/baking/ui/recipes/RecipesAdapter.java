package com.udacity.baking.ui.recipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private RecipesFragment.OnRecipeClickListener mClickCallback;

    RecipesAdapter(
            List<Recipe> recipes,
            RecipesFragment.OnRecipeClickListener clickCallback) {

        mRecipes = recipes;
        mClickCallback = clickCallback;
    }

    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recycler_item_recipe, parent, false);

        return new RecipesAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.ViewHolder viewHolder, int position) {
        Recipe recipe = mRecipes.get(position);

        viewHolder.tvRecipe.setText(recipe.getName());

        if (!recipe.getImage().equals("")) {
            viewHolder.image.setImageURI(recipe.getImage());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView image;
        TextView tvRecipe;

        ViewHolder(View itemView) {
            super(itemView);

            image = (SimpleDraweeView) itemView.findViewById(R.id.imageView);
            tvRecipe = (TextView) itemView.findViewById(R.id.tvRecipe);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickCallback.onRecipeSelected(mRecipes.get(position));
        }
    }
}