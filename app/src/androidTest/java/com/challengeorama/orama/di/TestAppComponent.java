package com.challengeorama.orama.di;

import android.app.Application;

import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.persistence.FundosDaoTest;
import com.challengeorama.orama.ui.main.MainFragmentTests;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                ViewModelFactoryModule.class,
                TestAppModule.class,
        }
)
public interface TestAppComponent extends AppComponent {

    @Component.Factory
    interface Factory {

        TestAppComponent create (@BindsInstance Application application);

    }

    void inject (MainFragmentTests mainFragmentTests);
    void inject (FundosDaoTest fundosDaoTest);

}
