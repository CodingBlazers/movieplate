package com.example.megha.movieplate.MovieFormat;

import com.example.megha.movieplate.MovieFormat.Movie;
import com.example.megha.movieplate.SignInPackage.account_access;
import com.example.megha.movieplate.SignInPackage.session_id;

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

    @GET("/3/movie/upcoming")
    Call<Movie> getUpcomingMovies(@Query("api_key") String key);

    @GET("/3/movie/now_playing")
    Call<Movie> getNowPlayingMovies(@Query("api_key") String key);

    @GET("/3/tv/airing_today")
    Call<Movie> getOnAirTVShows(@Query("api_key") String key);


    @GET("/3/authentication/token/new")
    Call<account_access> getRequestToken(@Query("api_key") String key);
    @GET("/3/authentication/token/validate_with_login")
    Call<account_access> getRequestAuthenticated(@Query("api_key") String key,@Query("request_token") String request_token,@Query("username") String username,@Query("password") String password);

    @GET("/3/authentication/session/new")
    Call<session_id> getSessionID(@Query("api_key") String key,@Query("request_token") String request_token);

}
