package com.challengeorama.orama.di;

import android.app.Application;

import androidx.room.Room;

import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.persistence.FundosDatabase;
import com.challengeorama.orama.repository.FakeDataSource;
import com.challengeorama.orama.repository.FakeFundosRepository;
import com.challengeorama.orama.repository.IFundosRepository;
import com.challengeorama.orama.util.JsonUITest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule {


    @Provides
    static IFundosRepository providesFakeFundosRepository(FakeDataSource fakeDataSource) {
        return new FakeFundosRepository(fakeDataSource);
    }

    @Singleton
    @Provides
    static FakeDataSource provideFakeDataSource(JsonUITest jsonUITest) {
        return new FakeDataSource(jsonUITest);
    }

    @Singleton
    @Provides
    static JsonUITest provideJsonUI(Application application) {
        return new JsonUITest(application);
    }

    @Singleton
    @Provides
    static FundosDatabase provideFakeTemTemDB(Application application) {
        return Room.inMemoryDatabaseBuilder(application, FundosDatabase.class).build();
    }

    @Singleton
    @Provides
    static FundosDao provideFakePatchesDao(FundosDatabase db) {
        return db.getFundosDao();
    }

}
