package com.challengeorama.orama.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.util.JsonUITest;
import com.challengeorama.orama.util.TestConstants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class FakeFundosRepository implements IFundosRepository {

    FakeDataSource fakeDataSource;


    @Inject
    public FakeFundosRepository(FakeDataSource fakeDataSource) {
        this.fakeDataSource = fakeDataSource;
    }


    @Override
    public LiveData<Fundos> getFundo(int id) {
        List<Fundos> result = fakeDataSource.getFakeDao();

        Fundos fundo = null;
        for (Fundos item : result) {
            if (item.getId() == id) {
                fundo = item;
            }
        }
        MutableLiveData<Fundos> returnedData = new MutableLiveData<>();
        returnedData.setValue(fundo);

        return returnedData;
    }

    @Override
    public LiveData<Resource<List<Fundos>>> getFundosSorted(@Nullable ListDataOptions filterOptions) {

        Resource<List<Fundos>> result = fakeDataSource.getApi();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();
        returnedData.setValue(result);

        return returnedData;
    }
}
