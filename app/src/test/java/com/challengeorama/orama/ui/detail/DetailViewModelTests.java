package com.challengeorama.orama.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.ui.main.MainViewModel;
import com.challengeorama.orama.util.JsonUnitTest;
import com.challengeorama.orama.util.LiveDataTestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DetailViewModelTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FundosRepository fundosRepository;

    private DetailViewModel viewModel;

    @Before
    public void setup() {
        fundosRepository = mock(FundosRepository.class);
        viewModel = new DetailViewModel(fundosRepository);
    }

    @Test
    public void getFundo() throws Exception {

        int fundoID = 99;

        //SETUP
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

        //RETURN
        when(fundosRepository.getFundo(fundoID)).thenReturn(returnedData);

        //GET
        Fundos observedData = liveDataTestUtil.getValue(viewModel.getFundo(fundoID));

        //ASSERT
        assertEquals(fundo, observedData);

        verify(fundosRepository).getFundo(fundoID);
        verifyNoMoreInteractions(fundosRepository);

    }


}
