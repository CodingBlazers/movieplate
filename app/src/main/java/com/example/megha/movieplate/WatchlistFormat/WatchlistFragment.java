package com.example.megha.movieplate.WatchlistFormat;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by megha on 24/6/16.
 */
public class WatchlistFragment extends Fragment {

    ViewPager movieWatchlistViewPager, tvShowWatchlistViewPager;
    String key, session_id, user_id;
    int numOfMoviesInWatchlist, numOfTVShowsInWatchlist;

    private FragmentStatePagerAdapter movieWatchlistAdapter;
    ArrayList<SingleWatchlistMovie> moviesInWatchlistArrayList;

    private FragmentStatePagerAdapter tvShowWatchlistAdapter;
    ArrayList<SingleWatchlistTVShow> tvShowInWatchlistArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.watchlist_fragment, container, false);

        tvShowWatchlistViewPager = (ViewPager) view.findViewById(R.id.containerTVShowWatchlist);

        movieWatchlistViewPager = (ViewPager) view.findViewById(R.id.containerMovieWatchlist);

        Bundle b = getArguments();
        key = b.getString(Constants.WATCHLIST_URL_API_KEY);
        session_id = b.getString(Constants.WATCHLIST_URL_SESSION_ID);
        user_id = b.getString(Constants.WATCHLIST_URL_USER_ID);

        final Call<WatchlistMovieJSON> getWatchlistMovie = ApiClientWatchlist.getInterface().getUserMovieWatchlist(user_id, key, session_id);

        getWatchlistMovie.enqueue(new Callback<WatchlistMovieJSON>() {

            @Override
            public void onResponse(Call<WatchlistMovieJSON> call, Response<WatchlistMovieJSON> response) {
                if (response.isSuccessful()) {
                    WatchlistMovieJSON watchlist = response.body();
                    moviesInWatchlistArrayList = watchlist.results;
                    numOfMoviesInWatchlist = moviesInWatchlistArrayList.size();
                    movieWatchlistAdapter = buildAdapter();
                    movieWatchlistViewPager.setAdapter(movieWatchlistAdapter);

                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                    moviesInWatchlistArrayList = null;
                    numOfMoviesInWatchlist = 0;
                }
            }

            @Override
            public void onFailure(Call<WatchlistMovieJSON> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                Log.i("Watchlist", "Issue occurred "+t.toString());
                moviesInWatchlistArrayList = null;
                numOfMoviesInWatchlist = 0;
            }
        });

        final Call<WatchlistTVShowJSON> getWatchlistTVShow = ApiClientWatchlist.getInterface().getUserTVShowWatchlist(user_id, key, session_id);

        getWatchlistTVShow.enqueue(new Callback<WatchlistTVShowJSON>() {

            @Override
            public void onResponse(Call<WatchlistTVShowJSON> call, Response<WatchlistTVShowJSON> response) {
                if (response.isSuccessful()) {
                    WatchlistTVShowJSON watchlist = response.body();

                    Log.i("Watchlist", "Issue occurred "+response.body().toString());
                    tvShowInWatchlistArrayList = watchlist.results;
                    numOfTVShowsInWatchlist = tvShowInWatchlistArrayList.size();
                    tvShowWatchlistAdapter = buildTVShowAdapter();
                    tvShowWatchlistViewPager.setAdapter(tvShowWatchlistAdapter);

                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                    tvShowInWatchlistArrayList = null;
                    Log.i("Watchlist", "Issue occurred "+response.code()+response.message());
                    numOfTVShowsInWatchlist = 0;
                }
            }

            @Override
            public void onFailure(Call<WatchlistTVShowJSON> call, Throwable t) {
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                Log.i("Watchlist", "Issue occurred "+t.toString());
                tvShowInWatchlistArrayList = null;
                numOfTVShowsInWatchlist = 0;
            }
        });

        return view;
    }


    private FragmentStatePagerAdapter buildAdapter() {
        return(new MovieWatchlistAdapter(getActivity(), getChildFragmentManager(), numOfMoviesInWatchlist, moviesInWatchlistArrayList));
    }

    private FragmentStatePagerAdapter buildTVShowAdapter() {
        return(new TVShowWatchlistAdapter(getActivity(), getChildFragmentManager(), numOfTVShowsInWatchlist, tvShowInWatchlistArrayList));
    }


    public static class MovieWatchlistFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_POSTER_PATH = "poster_path";

        public MovieWatchlistFragment(){
        }

        public static MovieWatchlistFragment newInstance(int sectionNumber, String posterPath) {
            MovieWatchlistFragment fragment = new MovieWatchlistFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_POSTER_PATH, posterPath);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.watchlist_movie_fragment, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.movieWatchlistFragmentImageView);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+getArguments().getString(ARG_POSTER_PATH)).into(imageView);
            return view;
        }
    }

    public static class TVShowWatchlistFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_POSTER_PATH = "poster_path";

        public TVShowWatchlistFragment(){
        }

        public static TVShowWatchlistFragment newInstance(int sectionNumber, String posterPath) {
            TVShowWatchlistFragment fragment = new TVShowWatchlistFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_POSTER_PATH, posterPath);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.watchlist_tvshow_fragment, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.tvShowWatchlistFragmentImageView);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+getArguments().getString(ARG_POSTER_PATH)).into(imageView);
            return view;
        }
    }

}
