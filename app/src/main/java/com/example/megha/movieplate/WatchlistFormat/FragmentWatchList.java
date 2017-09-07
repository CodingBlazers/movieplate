package com.example.megha.movieplate.WatchlistFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.MovieFormat.MovieDetails;
import com.example.megha.movieplate.MovieFormat.MovieList;
import com.example.megha.movieplate.MovieFormat.SingleMovieActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.TVFormat.TVDetails;
import com.example.megha.movieplate.TVFormat.TVList;
import com.example.megha.movieplate.TVFormat.TVShowDetails;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by megha on 07/09/17.
 */

public class FragmentWatchList extends Fragment implements Constants {

    View rootView;
    GridView mGridView;
    ProgressDialog progressDialog;

    SharedPreferencesUtils spUtils;
    MoviesWatchListAdapter mMovieAdapter;
    TVShowsWatchListAdapter mTVAdapter;

    ArrayList<MovieDetails> mMovieData;
    ArrayList<TVDetails> mTVShowData;

    Call<MovieList> watchlistMovieJSONCall;
    Call<TVList> watchlistTVShowJSONCall;

    String callType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_watchlist, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.watchlist_grid_view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");

        spUtils = new SharedPreferencesUtils(getActivity());

        watchlistMovieJSONCall = MovieDBApiClient.getInterface().getUserMovieWatchlist(spUtils.getIDKey(),
                BuildConfig.MOVIE_DB_API_KEY, spUtils.getSessionIDKey());
        watchlistTVShowJSONCall = MovieDBApiClient.getInterface().getUserTVShowWatchlist(spUtils.getIDKey(),
                BuildConfig.MOVIE_DB_API_KEY, spUtils.getSessionIDKey());

        callType = getArguments().getString(TYPE);

        if(callType.equals(MOVIE))
            makeMovieWatchListCall();
        else
            makeTVShowWatchListCall();
        return rootView;
    }

    private void makeMovieWatchListCall(){
        watchlistMovieJSONCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    mMovieData = response.body().results;
                    mMovieAdapter = new MoviesWatchListAdapter(getContext(), mMovieData);
                    mMovieAdapter.setOnItemClickListener(new MoviesWatchListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, MovieDetails movieDetails) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), SingleMovieActivity.class);
                            intent.putExtra(SINGLE_MOVIE_DETAILS, movieDetails);
                            startActivity(intent);
                        }
                    });
                    mGridView.setAdapter(mMovieAdapter);

                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void makeTVShowWatchListCall(){
        watchlistTVShowJSONCall.enqueue(new Callback<TVList>() {
            @Override
            public void onResponse(Call<TVList> call, Response<TVList> response) {
                if (response.isSuccessful()) {
                    mTVShowData = response.body().results;
                    mTVAdapter = new TVShowsWatchListAdapter(getContext(), mTVShowData);
                    mTVAdapter.setOnItemClickListener(new TVShowsWatchListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, TVDetails tvDetails) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), TVShowDetails.class);
                            intent.putExtra(SINGLE_TV_SHOW_DETAILS, tvDetails);
                            startActivity(intent);
                        }
                    });
                    mGridView.setAdapter(mTVAdapter);

                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TVList> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
