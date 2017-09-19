package com.udacity.baking.utils;

import android.support.v7.widget.RecyclerView;

public class AdapterUtils {

    public static void setAdapter(
            RecyclerView view,
            RecyclerView.Adapter adapter,
            RecyclerView.LayoutManager layoutManager) {

        view.setAdapter(adapter);
        view.setLayoutManager(layoutManager);
    }
}
