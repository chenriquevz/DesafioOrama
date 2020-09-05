package com.challengeorama.orama.di;

import android.app.Application;

import androidx.room.Room;

import com.challengeorama.orama.persistence.FundosDatabase;
import com.challengeorama.orama.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.challengeorama.orama.persistence.FundosDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static FundosDatabase provideFundosDatabase(Application application){
        return Room.databaseBuilder(
                application,
                FundosDatabase.class,
                DATABASE_NAME
        ).build();
    }


}
