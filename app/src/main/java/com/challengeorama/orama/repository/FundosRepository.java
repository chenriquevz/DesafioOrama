package com.challengeorama.orama.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.challengeorama.orama.api.ApiResponse;
import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.model.Fundos;
import com.challengeorama.orama.persistence.FundosDao;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FundosRepository {

    @NonNull
    private final FundosDao fundosDao;
    @NonNull
    private final MainApi mainApi;

    @Inject
    public FundosRepository(@NonNull FundosDao fundosDao, @NonNull MainApi mainApi) {
        this.fundosDao = fundosDao;
        this.mainApi = mainApi;
    }

    public LiveData<List<Fundos>> getFundo(int id) {
        return fundosDao.getFundo(id);
    }

    public LiveData<Resource<List<Fundos>>> observeFundos() {
        return new NetworkBoundResource<List<Fundos>, List<Fundos>>(AppExecutors.getInstance()) {


            @Override
            protected void saveCallResult(@NonNull List<Fundos> item) throws Exception {

                fundosDao.insertFundos(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Fundos> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Fundos>> loadFromDb() {
                return fundosDao.getFundos();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Fundos>>> createCall() {
                return mainApi.getFundos();
            }
        }.getAsLiveData();


    }
}
