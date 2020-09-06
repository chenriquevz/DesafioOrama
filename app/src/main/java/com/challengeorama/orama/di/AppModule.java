package com.challengeorama.orama.di;

import android.app.Application;

import androidx.room.Room;

import com.challengeorama.orama.api.LiveDataCallAdapterFactory;
import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.persistence.FundosDatabase;
import com.challengeorama.orama.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.challengeorama.orama.persistence.FundosDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
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

    @Singleton
    @Provides
    static FundosDao provideFundosDao(FundosDatabase db){
        return db.getFundosDao();
    }


    @Singleton
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

}
