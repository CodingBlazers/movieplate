package com.example.megha.movieplate.utility;

import com.example.megha.movieplate.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Megha on 27-03-2016.
 */
public interface ApiInterfaceOmdb {

    // In query it will append ? than parameter than equal to by itself.
    // Url request to OMDB Api for searching movie
    @GET(".")
    Call<Search> getMySearch(@Query("apikey") String apiKey, @Query("s") String item);

}
