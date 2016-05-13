package com.example.megha.movieplate.CelebsFormat;

import com.example.megha.movieplate.MovieFormat.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HIman$hu on 4/2/2016.
 */
public interface ApiInterfaceCelebDb {

    @GET("/3/person/popular")
    Call<Celebs> getPopularPerson(@Query("api_key") String key);

    @GET("/3/person/{id}")
    Call<CelebsDetails> getPersonDetails(@Path("id") String userId, @Query("api_key") String key);

}
