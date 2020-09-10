package com.challengeorama.orama.repository;

import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.util.JsonUITest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.challengeorama.orama.util.TestConstants.*;

@Singleton
public class FakeDataSource {

    public String daoReturn = FUNDOSJSON;
    public String apiReturn = FUNDOSJSON;

    JsonUITest jsonUITest;

    @Inject
    public FakeDataSource(JsonUITest jsonUITest) {
        this.jsonUITest = jsonUITest;
    }


    public List<Fundos> getFakeDao() {

        String data = jsonUITest.readJsonFromAsset(daoReturn);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();


        return new Gson().fromJson(data, reviewType);

    }


    public <T> Resource<List<Fundos>> getApi() {

        String data = jsonUITest.readJsonFromAsset(daoReturn);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> file = new Gson().fromJson(data, reviewType);

        MutableLiveData<List<Fundos>> returnedData = new MutableLiveData<>();
        returnedData.setValue(file);

        switch (apiReturn) {
            case NETWORK_ERROR: {

                return Resource.error(NETWORK_ERROR, file);
            }
            case NETWORK_ERROR_CACHELESS: {
                return Resource.error(NETWORK_ERROR_CACHELESS, null);
            }
            case LOADING: {
                return Resource.loading(null);
            }
            default: {
                return Resource.success(file);

            }
        }


    }



}
