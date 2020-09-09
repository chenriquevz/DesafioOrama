package com.challengeorama.orama.api;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.challengeorama.orama.model.fundos.Fundos;
import com.challengeorama.orama.util.JsonUnitTest;
import com.challengeorama.orama.util.LiveDataTestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.bouncycastle.util.encoders.UTF8;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class MainApiTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MainApi mainApi;

    MockWebServer mockWebServer;

    @Before
    public void setup() {

        mockWebServer = new MockWebServer();
        mainApi = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MainApi.class);
    }

    @After
    public void afterTest() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getFundos() throws Exception {

        enqueueResponse("fundos.json");

        String data = new JsonUnitTest().readJsonFromAsset("fundos.json");
        Type reviewType = new TypeToken<List<Fundos>>() {
        }.getType();
        List<Fundos> fundos = new Gson().fromJson(data, reviewType);

        LiveData<ApiResponse<List<Fundos>>> serviceList = mainApi.getFundos();

        LiveDataTestUtil<ApiResponse<List<Fundos>>> liveDataTestUtil = new LiveDataTestUtil<>();

        ApiResponse<List<Fundos>> observedData = liveDataTestUtil.getValue(serviceList);
        List<Fundos> ObservedResult = (List<Fundos>) processResponse((ApiResponse.ApiSuccessResponse) observedData);

        assertEquals(fundos.size(), ObservedResult.size());
        assertEquals(fundos.get(0).getId(), ObservedResult.get(0).getId());

        int count = mockWebServer.getRequestCount();
        assertEquals(1, count);

    }

    private List<Fundos> processResponse(ApiResponse.ApiSuccessResponse response) {
        return (List<Fundos>) response.getBody();
    }

    private void enqueueResponse(String filename) throws IOException {

        InputStream input = getClass().getClassLoader().getResourceAsStream("responses/" + filename);
        BufferedSource source = Okio.buffer(Okio.source(input));
        MockResponse mockResponse = new MockResponse();



        mockWebServer.enqueue(
                mockResponse.setBody(source.readByteString().string(StandardCharsets.UTF_8))
        );


    }

}
