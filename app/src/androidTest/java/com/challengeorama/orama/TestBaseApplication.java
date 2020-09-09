package com.challengeorama.orama;

import com.challengeorama.orama.di.DaggerTestAppComponent;

public class TestBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        appComponent = DaggerTestAppComponent.factory().create(this);
    }


}
