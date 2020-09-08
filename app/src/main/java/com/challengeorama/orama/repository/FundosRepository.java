package com.challengeorama.orama.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.challengeorama.orama.api.ApiResponse;
import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
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

    public LiveData<Fundos> getFundo(int id) {
        return fundosDao.getFundo(id);
    }

    public LiveData<Resource<List<Fundos>>> getFundosSorted(@Nullable ListDataOptions filterOptions) {

        return new NetworkBoundResource<List<Fundos>, List<Fundos>>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull List<Fundos> item) throws Exception {
                fundosDao.insertFundos(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Fundos> data) {
                return data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Fundos>> loadFromDb() {

                if (filterOptions != null) {
                    switch (filterOptions.getOption()) {
                        case MinimumAmount: {
                            return fundosDao.getFundosOrderMinimumAmount(filterOptions.getSort() == Sort.ASC);
                        }
                        case Date: {
                            return fundosDao.getFundosOrderDate(filterOptions.getSort() == Sort.ASC);
                        }
                        case profitabilityYear: {
                            return fundosDao.getFundosOrderProfitability(filterOptions.getSort() == Sort.ASC);
                        }
                        case Name: {
                            return fundosDao.getFundosOrderName(filterOptions.getSort() == Sort.ASC);
                        }
                        default: {
                            return fundosDao.getFundos();
                        }
                    }
                } else {
                    return fundosDao.getFundos();
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Fundos>>> createCall() {
                return mainApi.getFundos();
            }
        }.getAsLiveData();
    }
}
