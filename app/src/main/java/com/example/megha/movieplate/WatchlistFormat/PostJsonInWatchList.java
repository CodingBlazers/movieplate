package com.example.megha.movieplate.WatchlistFormat;

/**
 * Created by HIman$hu on 7/21/2016.
 */
public class PostJsonInWatchList {

    String media_type;
    int media_id;
    boolean watchlist;

    public PostJsonInWatchList(String media_type, int media_id, boolean watchlist) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.watchlist = watchlist;
    }
}
