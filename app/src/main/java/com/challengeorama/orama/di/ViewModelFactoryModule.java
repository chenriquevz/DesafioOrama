package com.challengeorama.orama.di;

import androidx.lifecycle.ViewModelProvider;


import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
