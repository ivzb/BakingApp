package com.udacity.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.baking.ui.recipes.RecipesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesActivityScreenTest {

    private static final String INGREDIENTS_NAME = "Ingredients";
    private static final String STEPS_NAME = "Steps";

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecyclerViewItem_OpensIngredientsActivity() {
        onView(withId(R.id.rvRecipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tvIngredients))
                .check(matches(isDisplayed()))
                .check(matches(withText(INGREDIENTS_NAME)));
        onView(withId(R.id.rvIngredients))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tvSteps))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(STEPS_NAME)));
        onView(withId(R.id.rvSteps))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}