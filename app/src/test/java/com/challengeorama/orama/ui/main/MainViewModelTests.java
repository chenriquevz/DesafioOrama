package com.challengeorama.orama.ui.main;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.challengeorama.orama.model.ListDataOptions;
import com.challengeorama.orama.model.Option;
import com.challengeorama.orama.model.Sort;
import com.challengeorama.orama.model.fundos.Fundos;
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
import java.lang.reflect.Type;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

        //SETUP
        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();
        returnedData.setValue(Resource.success(fundos));

        //RETURN
        when(fundosRepository.getFundosSorted(null)).thenReturn(returnedData);

        //GET
        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(viewModel.getFundosFiltered());

        //ASSERT
        Assert.assertEquals(fundos, observedData.data);

    }

    @Test
    public void getFundosFiltered_setListDataOptions() throws Exception {

        //SETUP
        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);
        List<Fundos> fundosFiltered = fundos.subList(0, 10);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();
        MutableLiveData<Resource<List<Fundos>>> returnedDataFiltered = new MutableLiveData<>();
        returnedData.setValue(Resource.success(fundos));
        returnedDataFiltered.setValue(Resource.success(fundosFiltered));

        ListDataOptions filter = new ListDataOptions(true, Sort.ASC, Option.Date);

        //RETURN
        when(fundosRepository.getFundosSorted(null)).thenReturn(returnedData);
        when(fundosRepository.getFundosSorted(filter)).thenReturn(returnedDataFiltered);

        //GET REGULAR
        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(viewModel.getFundosFiltered());

        //ASSERT
        Assert.assertEquals(fundos.size(), observedData.data.size());

        //CHANGE FILTER
        viewModel.setListDataOptions(filter);

        //GET NEW DATA
        observedData = liveDataTestUtil.getValue(viewModel.getFundosFiltered());

        //ASSERT
        Assert.assertEquals(fundosFiltered.size(), observedData.data.size());

        verify(fundosRepository).getFundosSorted(filter);
        verify(fundosRepository).getFundosSorted(null);
        verifyNoMoreInteractions(fundosRepository);


    }

    @Test
    public void getFundosFiltered_Error_And_Refresh() throws Exception {

        //SETUP
        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<Resource<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<List<Fundos>>> returnedData = new MutableLiveData<>();
        MutableLiveData<Resource<List<Fundos>>> returnedDataSuccess = new MutableLiveData<>();

        returnedData.setValue(Resource.error("error", null));
        returnedDataSuccess.setValue(Resource.success(fundos));

        //RETURN ERROR
        when(fundosRepository.getFundosSorted(null)).thenReturn(returnedData);

        //GET ERROR
        Resource<List<Fundos>> observedData = liveDataTestUtil.getValue(viewModel.getFundosFiltered());

        //ASSERT ERROR
        Assert.assertNull(observedData.data);
        Assert.assertEquals("error", observedData.message);

        //RETURN SUCCESS AND REFRESH
        when(fundosRepository.getFundosSorted(null)).thenReturn(returnedDataSuccess);
        viewModel.forceRefresh();

        //GET SUCCESS
        observedData = liveDataTestUtil.getValue(viewModel.getFundosFiltered());

        //ASSERT SUCCESS
        Assert.assertEquals(fundos.size(), observedData.data.size());
        Assert.assertEquals(fundos, observedData.data);
        Assert.assertNull(observedData.message);

        verify(fundosRepository, times(2)).getFundosSorted(null);
        verifyNoMoreInteractions(fundosRepository);


    }

}
