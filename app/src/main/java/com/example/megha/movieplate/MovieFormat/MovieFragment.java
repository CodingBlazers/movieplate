package com.example.megha.movieplate.MovieFormat;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Megha on 31-03-2016.
 */
public class MovieFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_fragment, container, false);
        Bundle b = getArguments();
        String key = b.getString(Constants.MOVIE_URL_API_KEY);
        Call<Movie> topRatedMovies = ApiClientMoviedb.getInterface().getTopRatedMovie(key);
        topRatedMovies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutTopRatedMovies, mf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
            }
        });
        Call<Movie> popularMovies = ApiClientMoviedb.getInterface().getPopularMovie(key);
        popularMovies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutPopularMovies, mf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
