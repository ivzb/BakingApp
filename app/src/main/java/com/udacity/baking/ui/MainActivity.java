package com.udacity.baking.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.udacity.baking.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnRecipeClickListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Change the GridView to space out the images more on tablet
            GridView gridView = findViewById(R.id.gvRecipes);
            gridView.setNumColumns(1);

            // Getting rid of the "Next" button that appears on phones for launching a separate activity
            Button nextButton = findViewById(R.id.btnNext);
            nextButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {

            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onRecipeSelected(int position) {

    }
}