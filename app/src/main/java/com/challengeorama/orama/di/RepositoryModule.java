package com.challengeorama.orama.di;

import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.repository.FundosRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    static FundosRepository provideFundosRepository(FundosDao fundosDao, MainApi mainApi){
        return new FundosRepository(fundosDao, mainApi);
    }

}
