package com.udacity.baking;

import com.facebook.stetho.Stetho;

public class BakingDebugApplication extends BakingApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho
                        .newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }
}