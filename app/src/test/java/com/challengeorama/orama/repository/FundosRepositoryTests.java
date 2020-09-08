package com.challengeorama.orama.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.api.ApiResponse;
import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.util.JsonUnitTest;
import com.challengeorama.orama.util.LiveDataTestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FundosRepositoryTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FundosRepository fundosRepository;

    private FundosDao fundosDao;

    private MainApi mainApi;

    @Before
    public void setup() {
        fundosDao = mock(FundosDao.class);
        mainApi = mock(MainApi.class);
        fundosRepository = new FundosRepository(fundosDao, mainApi);
    }


    @Test
    public void getFundoTest_get_valid() throws Exception {

        int fundoID = 1013;

        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Fundos> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Fundos> returnedData = new MutableLiveData<>();

        Fundos fundo = null;
        for (Fundos item : fundos) {
            if (item.getId() == fundoID) {
                fundo = item;
                returnedData.setValue(item);
            }
        }

        when(fundosDao.getFundo(fundoID)).thenReturn(returnedData);

        Fundos observedData = liveDataTestUtil.getValue(fundosRepository.getFundo(fundoID));

        assertEquals(fundo, observedData);
        assertEquals(fundoID, observedData.getId());

    }


    @Test
    public void getFundoTest_get_invalid() throws Exception {

        int fundoID = -10;

        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Fundos> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Fundos> returnedData = new MutableLiveData<>();

        Fundos fundo = null;
        for (Fundos item : fundos) {
            if (item.getId() == fundoID) {
                fundo = item;
                returnedData.setValue(item);
            }
        }

        when(fundosDao.getFundo(fundoID)).thenReturn(returnedData);

        Fundos observedData = liveDataTestUtil.getValue(fundosRepository.getFundo(fundoID));

        assertNull(observedData);

    }

    @Test
    public void getFundosSorted_get_valid() throws Exception {


        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Fundos>> returnedData = new MutableLiveData<>();
        returnedData.setValue(fundos);

        when(fundosDao.getFundos()).thenReturn(returnedData);

        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        assertEquals(fundos, observedData.data);
        assertNull(observedData.message);
        assertEquals(Resource.Status.SUCCESS, observedData.status);

    }

    @Test
    public void getFundosSorted_get_emptyLocal() throws Exception {

        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = Collections.emptyList();

        List<Fundos> fundosNetwork = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Fundos>> returnedData = new MutableLiveData<>();
        returnedData.setValue(fundos);
        when(fundosDao.getFundos()).thenReturn(returnedData);


        Response response = Response.success(200, fundosNetwork);
        ApiResponse apiResponse = new ApiResponse<List<Fundos>>().create(response);
        MutableLiveData<ApiResponse<List<Fundos>>> returnedDataNetwork = new MutableLiveData<>();
        when(mainApi.getFundos()).thenReturn(returnedDataNetwork);

        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        assertEquals(fundos, observedData.data);
        assertNull(observedData.message);
        assertEquals(Resource.Status.LOADING, observedData.status);

        liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        assertEquals(fundos, observedData.data);
        assertNull(observedData.message);
        assertEquals(Resource.Status.SUCCESS, observedData.status);

    }


}
