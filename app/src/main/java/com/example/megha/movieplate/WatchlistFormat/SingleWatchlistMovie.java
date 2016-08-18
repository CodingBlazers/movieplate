package com.example.megha.movieplate.WatchlistFormat;

/**
 * Created by megha on 23/6/16.
 */
public class SingleWatchlistMovie {

    String backdrop_path;
    int id;
    String original_title;
    String release_date;
    String poster_path;
    String title;
    double vote_average;
    int vote_count;

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }
}
