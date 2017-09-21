package com.udacity.baking;

import android.support.test.espresso.FailureHandler;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepsActivityScreenTest extends IngredientsActivityScreenTest {

    @Test
    public void clickPreviousButton_ClickUntilFirst() {
        super.clickRecyclerViewItem_OpensStepsActivity();

        final AtomicBoolean hasPrevious = new AtomicBoolean(true);

        while (hasPrevious.get()) {
            onView(withId(R.id.btnPrevious)).withFailureHandler(new FailureHandler() {
                @Override
                public void handle(Throwable error, Matcher<View> viewMatcher) {
                    hasPrevious.set(false);
                }
            }).check(matches(isDisplayed())).perform(click());
        }
    }

    @Test
    public void clickPreviousButton_ClickUntilLast() {
        super.clickRecyclerViewItem_OpensStepsActivity();

        final AtomicBoolean hasNext = new AtomicBoolean(true);

        while (hasNext.get()) {
            onView(withId(R.id.btnNext)).withFailureHandler(new FailureHandler() {
                @Override
                public void handle(Throwable error, Matcher<View> viewMatcher) {
                    hasNext.set(false);
                }
            }).check(matches(isDisplayed())).perform(click());
        }
    }
}
