package com.udacity.baking.utils;

import android.view.View;

public class ViewUtils {

    public static void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(View view) {
        view.setVisibility(View.GONE);
    }
}