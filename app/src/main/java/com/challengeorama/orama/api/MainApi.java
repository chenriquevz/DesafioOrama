package com.challengeorama.orama.api;

import androidx.lifecycle.LiveData;

import com.challengeorama.orama.model.fundos.Fundos;
import java.util.List;

import retrofit2.http.GET;

public interface MainApi {

    @GET("fund_detail_full.json?serializ%20er=fund_detail_full")
    LiveData<ApiResponse<List<Fundos>>> getFundos(
    );

}
