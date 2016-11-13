package com.example.megha.movieplate.MovieFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Megha on 31-03-2016.
 */
public class MovieFragment extends Fragment {

    View view;
    boolean b1, b2, paused;
    ProgressDialog pDialog;
    Call<Movie> topRatedMovies, popularMovies;
    Bundle b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_fragment, container, false);
        b = getArguments();
        paused = b1 = b2 = false;

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");

        return view;
    }
    @Override
    public void onResume() {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        pDialog.show();
        paused = false;
        String key = b.getString(Constants.MOVIE_URL_API_KEY);
        topRatedMovies = ApiClientMoviedb.getInterface().getTopRatedMovie(key);
        topRatedMovies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
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
            public void onFailure(Call<Movie> call, Throwable t) {
                // Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }
        });

        popularMovies = ApiClientMoviedb.getInterface().getPopularMovie(key);
        popularMovies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
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
            public void onFailure(Call<Movie> call, Throwable t) {
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

}
