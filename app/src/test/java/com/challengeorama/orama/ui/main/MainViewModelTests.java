package com.challengeorama.orama.ui.main;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.api.MainApi;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.persistence.FundosDao;
import com.challengeorama.orama.repository.FundosRepository;
import com.challengeorama.orama.repository.Resource;
import com.challengeorama.orama.util.JsonUnitTest;
import com.challengeorama.orama.util.LiveDataTestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robolectric.res.android.Asset;

import java.lang.reflect.Type;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainViewModelTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FundosRepository fundosRepository;

    private MainViewModel viewModel;

    @Before
    public void setup() {
        fundosRepository = mock(FundosRepository.class);
        viewModel = new MainViewModel(fundosRepository);
    }

    @Test
    public void getFundosFiltered() throws Exception {

        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();
        returnedData.setValue(Resource.success(fundos));

        when(fundosRepository.getFundosSorted(null)).thenReturn(returnedData);

        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(fundosRepository.getFundosSorted(null));

        Assert.assertEquals(fundos, observedData.data);

    }

}
