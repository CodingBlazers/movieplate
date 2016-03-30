package com.example.megha.movieplate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Megha on 27-03-2016.
 */
public interface ApiInterfaceOmdb {

    @GET
    Call<Search> getMySearch(@Url String urlString, @Query("t") String item);
}
