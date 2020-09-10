package com.challengeorama.orama.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.challengeorama.orama.api.ApiResponse;
import com.challengeorama.orama.util.LiveDataTestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class NetworkBoundResourceTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Test
    public void result_success() throws Exception {

        LiveDataTestUtil<Resource<String>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<String> returnedData = new MutableLiveData<>();
        returnedData.setValue("testando12345");

        LiveData<Resource<String>> inTest = new NetworkBoundResource<String, String>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return returnedData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                return null;
            }
        }.getAsLiveData();

        Resource<String> observedData = liveDataTestUtil.getValue(inTest);

        Assert.assertEquals("testando12345", observedData.data);
        Assert.assertNull(observedData.message);
        Assert.assertEquals(Resource.Status.SUCCESS, observedData.status);

    }

    @Test
    public void result_error() throws Exception {

        LiveDataTestUtil<Resource<String>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<String> returnedData = new MutableLiveData<>();
        returnedData.setValue(" ");

        Response response = Response.error(404, ResponseBody.create(null, "error123"));

        ApiResponse apiResponse = new ApiResponse<String>().create(response);
        MutableLiveData<ApiResponse<String>> returnedDataNetwork = new MutableLiveData<>();
        returnedDataNetwork.setValue(apiResponse);

        LiveData<Resource<String>> inTest = new NetworkBoundResource<String, String>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return returnedData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                return returnedDataNetwork;
            }
        }.getAsLiveData();

        Resource<String> observedData = liveDataTestUtil.getValue(inTest);

        Assert.assertEquals(Resource.Status.ERROR, observedData.status);
        Assert.assertEquals("error123", observedData.message);
        Assert.assertEquals(" ", observedData.data);



    }

}
