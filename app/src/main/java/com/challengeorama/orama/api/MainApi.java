package com.challengeorama.orama.api;

import com.challengeorama.orama.model.Fundos;
import java.util.List;
import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface MainApi {

    @GET()
    Flowable<List<Fundos>> getFundos(
    );

}
