package com.example.megha.movieplate.TVFormat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HIman$hu on 3/31/2016.
 */
public interface ApiInterfaceTVdb {

    @GET("/3/tv/popular")
    Call<TV> getPopularTvShows(@Query("api_key") String key);

    @GET("/3/tv/top_rated")
    Call<TV> getMostratedTvShows(@Query("api_key") String key);

    @GET("/3/tv/airing_today")
    Call<TV> getOnAirTVShows(@Query("api_key") String key);
}
