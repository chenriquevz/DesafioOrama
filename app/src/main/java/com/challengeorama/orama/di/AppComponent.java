package com.challengeorama.orama.di;

import android.app.Application;

import com.challengeorama.orama.ui.MainActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent {

    @Component.Factory
    interface Factory {

        public AppComponent create (@BindsInstance Application application);

    }

    public void inject(MainActivity mainActivity);
}
