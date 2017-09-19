package com.udacity.baking.ui.ingredients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;

    IngredientsAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recycler_item_ingredient, parent, false);

        return new IngredientsAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder viewHolder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        viewHolder.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
        viewHolder.tvMeasure.setText(ingredient.getMeasure());
        viewHolder.tvIngredient.setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuantity;
        TextView tvMeasure;
        TextView tvIngredient;

        ViewHolder(View itemView) {
            super(itemView);

            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvMeasure = itemView.findViewById(R.id.tvMeasure);
            tvIngredient = itemView.findViewById(R.id.tvIngredient);
        }
    }
}