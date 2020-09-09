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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


import retrofit2.Response;

import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class FundosRepositoryTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private IFundosRepository fundosRepository;

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

        String data = new JsonUnitTest().readJsonFromAsset(FUNDOSJSON);
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

        String data = new JsonUnitTest().readJsonFromAsset(FUNDOSJSON);
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


        String data = new JsonUnitTest().readJsonFromAsset(FUNDOSJSON);
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

        //carregando dados do JSON
        String data = new JsonUnitTest().readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = Collections.emptyList();

        //retorno empty do room
        List<Fundos> fundosNetwork = new Gson().fromJson(data, reviewType);
        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Fundos>> returnedData = new MutableLiveData<>();
        returnedData.setValue(fundos);
        when(fundosDao.getFundos()).thenReturn(returnedData);

        //Resposta da API
        Response response = Response.success(200, fundosNetwork);
        ApiResponse apiResponse = new ApiResponse<List<Fundos>>().create(response);
        MutableLiveData<ApiResponse<List<Fundos>>> returnedDataNetwork = new MutableLiveData<>();
        returnedDataNetwork.setValue(apiResponse);
        when(mainApi.getFundos()).thenReturn(returnedDataNetwork);

        //Busca da primeira resposta
        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        assertNull(observedData.data);
        assertNull(observedData.message);
        assertEquals(Resource.Status.LOADING, observedData.status);

        //populando o local com resultado do JSON
        returnedData.setValue(fundosNetwork);
        when(fundosDao.getFundos()).thenReturn(returnedData);

        observedData = liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        assertEquals(Resource.Status.SUCCESS, observedData.status);
        assertEquals(fundosNetwork.get(0), observedData.data.get(0));
        assertNull(observedData.message);


        verify(fundosDao, (times(2))).getFundos();
        verify(fundosDao).insertFundos(fundosNetwork);
        verifyNoMoreInteractions(fundosDao);
        verify(mainApi).getFundos();
        verifyNoMoreInteractions(mainApi);

    }


}
