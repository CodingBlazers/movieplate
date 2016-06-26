package com.example.megha.movieplate.WatchlistFormat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by megha on 25/6/16.
 */
public class TVShowWatchlistAdapter extends FragmentStatePagerAdapter {
    Context context = null;
    int numOfTVShowsInWatchlist;
    ArrayList<SingleWatchlistTVShow> tvShowsInWatchlistArrayList;

    public TVShowWatchlistAdapter(Context context, FragmentManager fm, int num, ArrayList<SingleWatchlistTVShow> arrayList) {
        super(fm);
        this.context = context;
        this.numOfTVShowsInWatchlist = num;
        this.tvShowsInWatchlistArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return numOfTVShowsInWatchlist;
    }

    @Override
    public Fragment getItem(int position) {
        String imagePath = tvShowsInWatchlistArrayList.get(position).poster_path;
        return WatchlistFragment.TVShowWatchlistFragment.newInstance(position + 1, imagePath);
    }

}
