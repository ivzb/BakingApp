package com.udacity.baking.ui.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.baking.R;
import com.udacity.baking.data.entities.Step;

import org.parceler.Parcels;

public class StepsFragment extends Fragment {

    private final static String StepKey = "step";

    private Step mStep;

    public StepsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(StepKey)) {
            Parcelable parcel = savedInstanceState.getParcelable(StepKey);
            setStep((Step) Parcels.unwrap(parcel));
        }

        final View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        TextView mTvDescription = rootView.findViewById(R.id.tvDescription);

        if (mStep != null) {
            mTvDescription.setText(mStep.getDescription());
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        if (mStep != null) {
            currentState.putParcelable(StepKey, Parcels.wrap(mStep));
        }
    }

    public void setStep(Step step) {
        mStep = step;
    }
}