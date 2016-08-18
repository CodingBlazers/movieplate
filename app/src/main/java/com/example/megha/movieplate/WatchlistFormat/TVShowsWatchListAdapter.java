package com.example.megha.movieplate.WatchlistFormat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

/**
 * Created by HIman$hu on 7/20/2016.
 */
public class TVShowsWatchListAdapter extends ArrayAdapter {

    Context context;
    String[] WatchListTvShowsPosterpath;

    public TVShowsWatchListAdapter(Context context,String[] WatchListTvShowsPosterpath) {
        super(context, 0,WatchListTvShowsPosterpath);
        this.context=context;
        this.WatchListTvShowsPosterpath=WatchListTvShowsPosterpath;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.movies_watchlist_imageview, null);
        }
        Picasso.with(context).load(WatchListTvShowsPosterpath[position]).resize(500, 600).into((ImageView) convertView);
        return convertView;
    }
}
