package com.example.megha.movieplate;

import com.example.megha.movieplate.MovieFormat.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Megha on 31-03-2016.
 */
public interface ApiInterfaceMoviedb {

    @GET("/3/movie/top_rated")
    Call<Movie> getTopRatedMovie(@Query("api_key") String key);

    @GET("/3/movie/popular")
    Call<Movie> getPopularMovie(@Query("api_key") String key);
}
