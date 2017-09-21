package com.udacity.baking.ui.ingredients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Step;

import org.w3c.dom.Text;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> mSteps;
    private IngredientsFragment.OnStepClickListener mClickCallback;

    StepsAdapter(
            List<Step> steps,
            IngredientsFragment.OnStepClickListener clickCallback) {

        mSteps = steps;
        mClickCallback = clickCallback;
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recycler_item_step, parent, false);

        return new StepsAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder viewHolder, int position) {
        Step step = mSteps.get(position);

        viewHolder.tvDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDescription;

        ViewHolder(View itemView) {
            super(itemView);

            tvDescription =  (TextView) itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickCallback.onStepSelected(position);
        }
    }
}