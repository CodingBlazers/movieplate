package com.example.megha.movieplate;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.MovieFormat.Movie;
import com.example.megha.movieplate.MovieFormat.MovieLinearLayoutFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        Bundle b = getArguments();
        String key = b.getString(Constants.MOVIE_URL_API_KEY);
        Call<Movie> now_playing_movies = ApiClientMoviedb.getInterface().getNowPlayingMovies(key);
        now_playing_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Home_activity_linear_layout mf = new Home_activity_linear_layout();
                Bundle b = new Bundle();
                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                mf.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutnowplayingMovies, mf).commit();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        Call<Movie> upcoming_movies=ApiClientMoviedb.getInterface().getUpcomingMovies(key);
        upcoming_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Home_activity_linear_layout mf = new Home_activity_linear_layout();
                Bundle b = new Bundle();
                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                mf.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutupcomingmovies, mf).commit();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        Call<Movie> on_air_tv_shows=ApiClientMoviedb.getInterface().getOnAirTVShows(key);
        on_air_tv_shows.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Home_activity_linear_layout mf = new Home_activity_linear_layout();
                Bundle b = new Bundle();
                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                mf.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutonairTvshows, mf).commit();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        return view;
    }
}
