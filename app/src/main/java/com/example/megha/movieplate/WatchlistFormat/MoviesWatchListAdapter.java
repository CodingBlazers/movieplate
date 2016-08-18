package com.example.megha.movieplate.WatchlistFormat;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HIman$hu on 7/20/2016.
 */
public class MoviesWatchListAdapter extends ArrayAdapter {

    Context context;
    String[] WatchListMoviesPosterpath;

    public MoviesWatchListAdapter(Context context, String[] WatchListMoviesPosterpath) {
        super(context, R.layout.movies_watchlist_imageview, WatchListMoviesPosterpath);
        this.context = context;
        this.WatchListMoviesPosterpath = WatchListMoviesPosterpath;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.movies_watchlist_imageview, null);
        }
        Picasso.with(context).load(WatchListMoviesPosterpath[position]).resize(500,600).into((ImageView) convertView);
        return convertView;
    }
}
