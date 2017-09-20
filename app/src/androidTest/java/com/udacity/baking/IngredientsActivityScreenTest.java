package com.udacity.baking;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IngredientsActivityScreenTest extends RecipesActivityScreenTest {

    @Test
    public void clickRecyclerViewItem_OpensStepsActivity() {
        super.clickRecyclerViewItem_OpensIngredientsActivity();

        onView(withId(R.id.rvSteps))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tvDescription))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnPrevious))
                .check(matches(isDisplayed()));
        onView(withId(R.id.btnNext))
                .check(matches(isDisplayed()));
    }
}