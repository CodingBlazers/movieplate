package com.example.megha.movieplate.MovieFormat;

import java.io.Serializable;

/**
 * Created by Megha on 31-03-2016.
 */

public class MovieDetails implements Serializable{

    //Made the hierarchy. Will use that in future. For now, we only need link of posters stored in poster_path.
    String poster_path;
    public String adult;
    public String overview;
    public String release_date;
    public int id;
    public String title;

    public int getId() {
        return id;
    }

    public String original_language;
    public String popularity;
    public String vote_count;
    public String vote_average;
    private int rating;

    public String getPoster_path(){
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRating() {
        return rating;
    }
}
