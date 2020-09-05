package com.challengeorama.orama;


import android.app.Application;

import com.challengeorama.orama.di.AppComponent;
import com.challengeorama.orama.di.DaggerAppComponent;

public class BaseApplication extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    public void initAppComponent() {
        appComponent = DaggerAppComponent.factory().create(this);
    }

}






