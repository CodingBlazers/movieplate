package com.example.megha.movieplate.WatchlistFormat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by megha on 24/6/16.
 */
public interface ApiInterfaceWatchlist {

    @GET("/3/account/{id}/watchlist/movies")
    Call<WatchlistMovieJSON> getUserMovieWatchlist(@Path("id") String user_id, @Query("api_key") String key, @Query("session_id") String session_id);

    @GET("/3/account/{id}/watchlist/tv")
    Call<WatchlistTVShowJSON> getUserTVShowWatchlist(@Path("id") String user_id, @Query("api_key") String key, @Query("session_id") String session_id);

}
