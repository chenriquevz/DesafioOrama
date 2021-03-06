package com.challengeorama.orama.persistence;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.challengeorama.orama.TestBaseApplication;
import com.challengeorama.orama.di.TestAppComponent;
import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.ui.BaseFragmentTest;
import com.challengeorama.orama.util.JsonUITest;
import com.challengeorama.orama.util.LiveDataTestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import static com.challengeorama.orama.util.TestConstants.FUNDOSJSON;

@RunWith(AndroidJUnit4.class)
public class FundosDaoTest extends BaseFragmentTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Inject
    JsonUITest jsonUITest;

    @Inject
    FundosDao fundosDao;

    @Before
    public void setup() {

        TestBaseApplication app = (TestBaseApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();

        injectTest(app);


    }

    @Test
    public void getFundos() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundos();

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Integer.compare(t2.getId(), t1.getId());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getId(), observedData.get(i).getId());
        }

    }


    @Test
    public void getFundo() throws Exception {

        int fundoID = 99;

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);
        LiveDataTestUtil<Fundos> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<Fundos> result = fundosDao.getFundo(fundoID);

        Fundos observedData = liveDataTestUtil.getValue(result);

        Fundos fundo = null;
        for (Fundos item : fundos) {
            if (item.getId() == fundoID) {
                fundo = item;
            }
        }

        Assert.assertEquals(fundo.getId(), observedData.getId());

    }

    @Test
    public void getFundosOrderName_asc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderName(true);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t2.getSimpleName().compareTo(t1.getSimpleName());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getSimpleName(), observedData.get(i).getSimpleName());
        }

    }

    @Test
    public void getFundosOrderName_dsc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderName(false);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t1.getSimpleName().compareTo(t2.getSimpleName());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getSimpleName(), observedData.get(i).getSimpleName());
        }

    }

    @Test
    public void getFundosOrderDate_dsc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderDate(false);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t1.getInitialDate().compareTo(t2.getInitialDate());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getInitialDate(), observedData.get(i).getInitialDate());
        }

    }


    @Test
    public void getFundosOrderDate_asc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderDate(true);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return t2.getInitialDate().compareTo(t1.getInitialDate());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getInitialDate(), observedData.get(i).getInitialDate());
        }

    }


    @Test
    public void getFundosOrderProfitability_asc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderProfitability(true);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t2.getProfitabilities().getYear(), t1.getProfitabilities().getYear());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getProfitabilities().getYear(), observedData.get(i).getProfitabilities().getYear());
        }

    }

    @Test
    public void getFundosOrderProfitability_dsc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderProfitability(false);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t1.getProfitabilities().getYear(), t2.getProfitabilities().getYear());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getProfitabilities().getYear(), observedData.get(i).getProfitabilities().getYear());
        }

    }

    @Test
    public void getFundosOrderMinimumAmount_dsc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderMinimumAmount(false);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t1.getOperability().getMinimumInitialApplicationAmount(), t2.getOperability().getMinimumInitialApplicationAmount());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getOperability().getMinimumInitialApplicationAmount(), observedData.get(i).getOperability().getMinimumInitialApplicationAmount());
        }

    }

    @Test
    public void getFundosOrderMinimumAmount_asc() throws Exception {

        String data = jsonUITest.readJsonFromAsset(FUNDOSJSON);
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveDataTestUtil<List<Fundos>> liveDataTestUtil = new LiveDataTestUtil<>();

        fundosDao.insertFundos(fundos);

        LiveData<List<Fundos>> listResult = fundosDao.getFundosOrderMinimumAmount(true);

        List<Fundos> observedData = liveDataTestUtil.getValue(listResult);

        Collections.sort(fundos, new Comparator<Fundos>() {
            @Override
            public int compare(Fundos t2, Fundos t1) {
                return Float.compare(t2.getOperability().getMinimumInitialApplicationAmount(), t1.getOperability().getMinimumInitialApplicationAmount());
            }
        });

        Assert.assertEquals(fundos.size(), observedData.size());

        for (int i = 0; i < fundos.size(); i++) {
            Assert.assertEquals(fundos.get(i).getOperability().getMinimumInitialApplicationAmount(), observedData.get(i).getOperability().getMinimumInitialApplicationAmount());
        }

    }

    @Override
    public void injectTest(TestBaseApplication application) {
        ((TestAppComponent) application.appComponent).inject(this);
    }
}
