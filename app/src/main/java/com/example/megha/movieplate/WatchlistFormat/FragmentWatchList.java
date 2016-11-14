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
import android.widget.ListView;
import android.widget.Toast;

import com.example.megha.movieplate.ApiClientOmdb;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.MovieFormat.SingleMovieActivity;
import com.example.megha.movieplate.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.Search;
import com.example.megha.movieplate.SearchFragment;
import com.example.megha.movieplate.utility.ConnectionDetector;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 7/20/2016.
 */
public class FragmentWatchList extends Fragment {


    String key, session_id, user_id;
    ArrayList<SingleWatchlistMovie> MoviesArrayWatchList;
    ArrayList<SingleWatchlistTVShow> TVShowsArrayWatchlist;
    GridView MoviesWatchListGV, TvShowsWatchListGV;
    WatchlistMovieJSON moviesWatchList;
    WatchlistTVShowJSON TVShowWatchList;
    boolean b1, b2;
    ProgressDialog progressDialog;
    boolean paused;
    Bundle b;

    Call<WatchlistMovieJSON> MoviesWatchListResponse;
    Call<WatchlistTVShowJSON> TVWatchListResponse;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        b = getArguments();
        key = b.getString(Constants.WATCHLIST_URL_API_KEY);
        session_id = b.getString(Constants.WATCHLIST_URL_SESSION_ID);
        user_id = b.getString(Constants.WATCHLIST_URL_USER_ID);

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        MoviesWatchListGV = (GridView) view.findViewById(R.id.id_MoviesWatchList);
        MoviesWatchListGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = moviesWatchList.results.get(position).getOriginal_title();
                Call<Search> mySearch = ApiClientOmdb.getInterface().getMySearch("", title);
                mySearch.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            Search s = response.body();
                            Bundle b = new Bundle();
                            SearchFragment sf = new SearchFragment();
                            b.putSerializable("SearchContent", s);
                            sf.setArguments(b);
                            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
                        } else {
                            Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TvShowsWatchListGV = (GridView) view.findViewById(R.id.id_TVshowsWatchList);
        TvShowsWatchListGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = TVShowWatchList.results.get(position).getOriginal_name();
                Call<Search> mySearch = ApiClientOmdb.getInterface().getMySearch("", title);
                mySearch.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            Search s = response.body();
                            Bundle b = new Bundle();
                            SearchFragment sf = new SearchFragment();
                            b.putSerializable("SearchContent", s);
                            sf.setArguments(b);
                            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
                        } else {
                            Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
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
        MoviesWatchListResponse = ApiClientWatchlist.getInterface().getUserMovieWatchlist(user_id, key, session_id);
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

                    //MoviesWatchListAdapter moviesWatchListAdapter = new MoviesWatchListAdapter(getContext(), MoviesposterPaths);
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

        TVWatchListResponse = ApiClientWatchlist.getInterface().getUserTVShowWatchlist(user_id, key, session_id);
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

                    //TVShowsWatchListAdapter tvShowsWatchListAdapter = new TVShowsWatchListAdapter(getContext(), TvShowsposterPaths);
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
}
