package com.example.megha.movieplate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Megha on 27-03-2016.
 */
public interface ApiInterfaceOmdb {

    @GET("t={item}")
    Call<Search> getMySearch(@Path("item") String item);
}
