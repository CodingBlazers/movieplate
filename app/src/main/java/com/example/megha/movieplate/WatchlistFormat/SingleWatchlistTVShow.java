package com.example.megha.movieplate.WatchlistFormat;

/**
 * Created by megha on 25/6/16.
 */
public class SingleWatchlistTVShow {
    String backdrop_path;
    int id;
    String original_name;
    String first_air_date;
    String poster_path;
    double popularity;
    String name;
    double vote_average;
    int vote_count;

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_name() {
        return original_name;
    }
}
