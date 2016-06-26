package com.example.megha.movieplate.WatchlistFormat;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by megha on 25/6/16.
 */

public class MovieWatchlistAdapter extends FragmentStatePagerAdapter {
    Context context = null;
    int numOfMoviesInWatchlist;
    ArrayList<SingleWatchlistMovie> moviesInWatchlistArrayList;

    public MovieWatchlistAdapter(Context context, FragmentManager fm, int num, ArrayList<SingleWatchlistMovie> arrayList) {
        super(fm);
        this.context = context;
        this.numOfMoviesInWatchlist = num;
        this.moviesInWatchlistArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return numOfMoviesInWatchlist;
    }

    @Override
    public Fragment getItem(int position) {
        String imagePath = moviesInWatchlistArrayList.get(position).poster_path;
        return WatchlistFragment.MovieWatchlistFragment.newInstance(position + 1, imagePath);
    }
}