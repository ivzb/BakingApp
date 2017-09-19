package com.udacity.baking.ui.steps;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.baking.R;
import com.udacity.baking.data.entities.Recipe;
import com.udacity.baking.data.entities.Step;
import com.udacity.baking.ui.ingredients.IngredientsActivity;
import com.udacity.baking.utils.BoundsUtils;

import org.parceler.Parcels;

import static com.udacity.baking.utils.ViewUtils.hide;
import static com.udacity.baking.utils.ViewUtils.show;

public class StepsActivity extends AppCompatActivity {

    private static final String RecipeKey = "recipe";
    private static final String PositionKey = "position";

    private Recipe mRecipe;
    private int mPosition;

    private Button mBtnPrevious;
    private Button mBtnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RecipeKey)) {
                Parcelable parcel = savedInstanceState.getParcelable(RecipeKey);
                mRecipe = Parcels.unwrap(parcel);
            }

            mPosition = savedInstanceState.getInt(PositionKey);
        }

        mBtnPrevious = findViewById(R.id.btnPrevious);
        mBtnNext = findViewById(R.id.btnNext);

        mBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = mRecipe.getSteps().size();

                if (BoundsUtils.isInBounds(mPosition - 1, size)) {
                    mPosition--;
                    initStepsFragment();
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = mRecipe.getSteps().size();

                if (BoundsUtils.isInBounds(mPosition + 1, size)) {
                    mPosition++;
                    initStepsFragment();
                }
            }
        });

        if (savedInstanceState == null) {
            mPosition = getIntent().getIntExtra(IngredientsActivity.StepPositionExtra, -1);
            Parcelable parcel = getIntent().getParcelableExtra(IngredientsActivity.RecipeExtra);
            mRecipe = Parcels.unwrap(parcel);

            int size = mRecipe.getSteps().size();

            if (BoundsUtils.isInBounds(mPosition, size)) {
                initStepsFragment();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipe != null) {
            outState.putParcelable(RecipeKey, Parcels.wrap(mRecipe));
        }

        outState.putInt(PositionKey, mPosition);
    }

    private void initStepsFragment() {
        Step step = mRecipe.getSteps().get(mPosition);

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setStep(step);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_steps, stepsFragment)
                .commit();

        int size = mRecipe.getSteps().size();

        if (!BoundsUtils.isInBounds(mPosition - 1, size)) {
            hide(mBtnPrevious);
        } else {
            show(mBtnPrevious);
        }

        if (!BoundsUtils.isInBounds(mPosition + 1, size)) {
            hide(mBtnNext);
        } else {
            show(mBtnNext);
        }
    }
}
