package com.example.megha.movieplate;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.MovieFormat.Movie;
import com.example.megha.movieplate.MovieFormat.MovieLinearLayoutFragment;
import com.example.megha.movieplate.TVFormat.ApiClientTVDb;
import com.example.megha.movieplate.TVFormat.TV;
import com.example.megha.movieplate.TVFormat.TVLinearlayoutFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ProgressDialog pDialog;
    Call<Movie> now_playing_movies;
    Call<Movie> upcoming_movies;
    Call<TV> on_air_tv_shows;
    boolean paused;
    boolean b1, b2, b3;
    Bundle b;

    @Nullable
    @Override
    //From onCreateview we get to know about what type of view we have to attach(landscape/portrait)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        b = getArguments();
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
        pDialog.show();
        paused = false;
        String key = b.getString(Constants.MOVIE_URL_API_KEY);
        now_playing_movies = ApiClientMoviedb.getInterface().getNowPlayingMovies(key);
        now_playing_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Log.i("Movie Object",movie.results.toString());
                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                mf.setArguments(b);
                if(paused == false)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutnowplayingMovies, mf).commit();
                b1 = true;
                if(b3 && b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                //  Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1 = true;
                if(b3 && b2)
                    pDialog.dismiss();
            }
        });
        upcoming_movies = ApiClientMoviedb.getInterface().getUpcomingMovies(key);
        upcoming_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
                mf.setArguments(b);
                if(paused == false)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutupcomingmovies, mf).commit();
                b2 = true;
                if(b1 && b3)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                //    Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b2 = true;
                if(b1 && b3)
                    pDialog.dismiss();
            }
        });
        on_air_tv_shows = ApiClientTVDb.getInterface().getOnAirTVShows(key);
        on_air_tv_shows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                TV tv = response.body();
                TVLinearlayoutFragment mf = new TVLinearlayoutFragment();
                Bundle b = new Bundle();
                b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
                mf.setArguments(b);
                if(paused == false)
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutonairTvshows, mf).commit();
                b3 = true;
                if(b1 && b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {
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
        if(pDialog.isShowing())
            pDialog.dismiss();
        now_playing_movies.cancel();
        upcoming_movies.cancel();
        on_air_tv_shows.cancel();
        super.onPause();
    }
}
