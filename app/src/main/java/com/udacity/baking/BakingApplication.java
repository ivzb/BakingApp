package com.udacity.baking;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class BakingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}