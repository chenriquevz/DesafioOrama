package com.challengeorama.orama.di;

import android.app.Application;

import com.challengeorama.orama.ui.MainActivity;
import com.challengeorama.orama.ui.main.MainFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                ViewModelFactoryModule.class,
                RepositoryModule.class
        }
)
public interface AppComponent {

    @Component.Factory
    interface Factory {

        AppComponent create (@BindsInstance Application application);

    }

    void inject(MainFragment mainFragment);
    void inject(MainActivity mainActivity);
}
