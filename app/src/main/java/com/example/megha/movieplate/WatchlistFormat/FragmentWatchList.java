package com.example.megha.movieplate.WatchlistFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.SearchFragment;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 7/20/2016.
 */
public class FragmentWatchList extends Fragment implements Constants{

    String key, session_id, user_id;
    ArrayList<SingleWatchlistMovie> MoviesArrayWatchList;
    ArrayList<SingleWatchlistTVShow> TVShowsArrayWatchlist;
    GridView MoviesWatchListGV, TvShowsWatchListGV;
    WatchlistMovieJSON moviesWatchList;
    WatchlistTVShowJSON TVShowWatchList;
    boolean b1, b2;
    ProgressDialog progressDialog;
    boolean paused;
    SharedPreferencesUtils spUtils;

    Call<WatchlistMovieJSON> MoviesWatchListResponse;
    Call<WatchlistTVShowJSON> TVWatchListResponse;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        spUtils = new SharedPreferencesUtils(getActivity());
        key = spUtils.getAPIKey();
        session_id = spUtils.getSessionIDKey();
        user_id = spUtils.getIDKey();

        checkConnectivity();

        MoviesWatchListGV = (GridView) view.findViewById(R.id.id_MoviesWatchList);
        MoviesWatchListGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                SearchFragment sf = new SearchFragment();
                b.putSerializable(SEARCH_TITLE, moviesWatchList.results.get(position).getOriginal_title());
                sf.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
            }
        });

        TvShowsWatchListGV = (GridView) view.findViewById(R.id.id_TVshowsWatchList);
        TvShowsWatchListGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                SearchFragment sf = new SearchFragment();
                b.putSerializable(SEARCH_TITLE, TVShowWatchList.results.get(position).getOriginal_name());
                sf.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");

        b1=false; b2=false;
        Log.i("Credentials", key + "\n" + session_id + "\n" + user_id);

        //Call to fetch movies from the user WatchList
        MoviesWatchListResponse = MovieDBApiClient.getInterface().getUserMovieWatchlist(user_id, key, session_id);
        MoviesWatchListResponse.enqueue(new Callback<WatchlistMovieJSON>() {
            @Override
            public void onResponse(Call<WatchlistMovieJSON> call, Response<WatchlistMovieJSON> response) {
                if (response.isSuccessful()) {
                    moviesWatchList = response.body();
                    MoviesArrayWatchList = moviesWatchList.results;

                    String[] MoviesposterPaths = new String[MoviesArrayWatchList.size()];

                    for (int i = 0; i < MoviesArrayWatchList.size(); i++) {
                        MoviesposterPaths[i] = "http://image.tmdb.org/t/p/w300/" + MoviesArrayWatchList.get(i).getPoster_path().toString();
                    }

                    MoviesWatchListGV.setAdapter(new MoviesWatchListAdapter(getContext(), MoviesposterPaths));

                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b1 = true;
                if(b2)
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WatchlistMovieJSON> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                b1 = true;
                if(b2)
                    progressDialog.dismiss();
            }
        });

        TVWatchListResponse = MovieDBApiClient.getInterface().getUserTVShowWatchlist(user_id, key, session_id);
        TVWatchListResponse.enqueue(new Callback<WatchlistTVShowJSON>() {
            @Override
            public void onResponse(Call<WatchlistTVShowJSON> call, Response<WatchlistTVShowJSON> response) {

                if (response.isSuccessful()) {
                    TVShowWatchList = response.body();
                    TVShowsArrayWatchlist = TVShowWatchList.results;

                    String[] TvShowsposterPaths = new String[TVShowsArrayWatchlist.size()];

                    for (int i = 0; i < TVShowsArrayWatchlist.size(); i++) {
                        TvShowsposterPaths[i] = "http://image.tmdb.org/t/p/w300/" + TVShowsArrayWatchlist.get(i).getPoster_path() + "";
                    }

                    TvShowsWatchListGV.setAdapter(new TVShowsWatchListAdapter(getContext(), TvShowsposterPaths));
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b2 = true;
                if(b1)
                    progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<WatchlistTVShowJSON> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                b2 = true;
                if(b1)
                    progressDialog.dismiss();
            }
        });
        super.onResume();
    }

    @Override
    public void onPause() {
        paused = true;
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        MoviesWatchListResponse.cancel();
        TVWatchListResponse.cancel();
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
