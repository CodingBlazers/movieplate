package com.example.megha.movieplate.MovieFormat;

/**
 * Created by Megha on 31-03-2016.
 */
public class Results {
    //Made the hierarchy. Will use that in future. For now, we only need link of posters stored in poster_path.
    String poster_path;
    String adult;
    String overview;
    String release_date;
    String id;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    String popularity;
    String vote_count;
    String video;
    String vote_average;

    public String getPoster_path(){
        return poster_path;
    }
}
