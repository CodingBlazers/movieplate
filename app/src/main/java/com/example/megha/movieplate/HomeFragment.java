package com.example.megha.movieplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.megha.movieplate.MovieFormat.MovieList;
import com.example.megha.movieplate.MovieFormat.MovieLinearLayoutFragment;
import com.example.megha.movieplate.TVFormat.TVList;
import com.example.megha.movieplate.TVFormat.TVLinearLayoutFragment;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements Constants{

    ProgressDialog pDialog;
    Call<MovieList> now_playing_movies;
    Call<MovieList> upcoming_movies;
    Call<TVList> on_air_tv_shows;
    boolean paused;
    boolean b1, b2, b3;
    SharedPreferencesUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        spUtils = new SharedPreferencesUtils(getActivity());
        paused = false;
        b1 = b2 = b3 = false;

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");

        ScrollView root = (ScrollView) view.findViewById(R.id.root_home_fragment);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
            }
        });

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

        now_playing_movies = MovieDBApiClient.getInterface().getNowPlayingMovies(BuildConfig.MOVIE_DB_API_KEY);
        now_playing_movies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MovieList movie = response.body();
                Log.i("MovieList Object",movie.results.toString());
                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(ALL_MOVIE_DETAILS, movie.results);
                mf.setArguments(b);
                if(!paused)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutnowplayingMovies, mf).commit();
                b1 = true;
                if(b3 && b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                //  Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1 = true;
                if(b3 && b2)
                    pDialog.dismiss();
            }
        });
        upcoming_movies = MovieDBApiClient.getInterface().getUpcomingMovies(BuildConfig.MOVIE_DB_API_KEY);
        upcoming_movies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MovieList movie = response.body();
                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(ALL_MOVIE_DETAILS, movie.results);
                mf.setArguments(b);
                if(!paused)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutupcomingmovies, mf).commit();
                b2 = true;
                if(b1 && b3)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                //    Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b2 = true;
                if(b1 && b3)
                    pDialog.dismiss();
            }
        });
        on_air_tv_shows = MovieDBApiClient.getInterface().getOnAirTVShows(BuildConfig.MOVIE_DB_API_KEY);
        on_air_tv_shows.enqueue(new Callback<TVList>() {
            @Override
            public void onResponse(Call<TVList> call, Response<TVList> response) {
                TVList tvList = response.body();
                TVLinearLayoutFragment mf = new TVLinearLayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(ALL_TV_SHOW_DETAILS, tvList.results);
                mf.setArguments(b);
                if(!paused)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutonairTvshows, mf).commit();
                b3 = true;
                if(b1 && b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TVList> call, Throwable t) {
                //    Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b3 = true;
                if(b1 && b2)
                    pDialog.dismiss();
            }
        });
        super.onResume();
    }

    @Override
    public void onPause() {
        paused = true;
        if(pDialog.isShowing())
            pDialog.dismiss();
        now_playing_movies.cancel();
        upcoming_movies.cancel();
        on_air_tv_shows.cancel();
        super.onPause();
    }
}
