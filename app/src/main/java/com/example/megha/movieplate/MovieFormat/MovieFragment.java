package com.example.megha.movieplate.MovieFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Megha on 31-03-2016.
 */
public class MovieFragment extends Fragment implements Constants{

    View view;
    boolean b1, b2, paused;
    ProgressDialog pDialog;
    Call<MovieList> topRatedMovies, popularMovies;
    SharedPreferencesUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_fragment, container, false);
        paused = b1 = b2 = false;
        spUtils = new SharedPreferencesUtils(getActivity());

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");

        return view;
    }
    @Override
    public void onResume() {
        checkConnectivity();
        pDialog.show();
        paused = false;
        topRatedMovies = MovieDBApiClient.getInterface().getTopRatedMovie(BuildConfig.MOVIE_DB_API_KEY);
        topRatedMovies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    MovieList movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(ALL_MOVIE_DETAILS, movie.results);
                    mf.setArguments(b);
                    if(paused == false)
                        getFragmentManager().beginTransaction().replace(R.id.frameLayoutTopRatedMovies, mf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }
        });

        popularMovies = MovieDBApiClient.getInterface().getPopularMovie(BuildConfig.MOVIE_DB_API_KEY);
        popularMovies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    MovieList movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(ALL_MOVIE_DETAILS, movie.results);
                    mf.setArguments(b);
                    if(paused == false)
                        getFragmentManager().beginTransaction().replace(R.id.frameLayoutPopularMovies, mf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b2=true;
                if(b1)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                // Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b2=true;
                if(b1)
                    pDialog.dismiss();
            }
        });
        super.onResume();
    }

    @Override
    public void onPause() {
        if(pDialog.isShowing())
            pDialog.dismiss();
        topRatedMovies.cancel();
        popularMovies.cancel();
        super.onPause();
    }

    private void checkConnectivity() {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
