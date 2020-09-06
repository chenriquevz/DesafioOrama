package com.challengeorama.orama.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.challengeorama.orama.ui.main.MainViewModel;
import com.challengeorama.orama.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);

}
