package com.example.megha.movieplate.MovieFormat;

import java.io.Serializable;

/**
 * Created by Megha on 31-03-2016.
 */
public class Results implements Serializable {
    //Made the hierarchy. Will use that in future. For now, we only need link of posters stored in poster_path.
    String poster_path;
    public String adult;
    public String overview;
    public String release_date;
    public String id;
    public String title;
    public String original_language;
    public String popularity;
    public String vote_count;
    public String vote_average;
    /*public String original_title;
    public String backdrop_path;
    public String video;*/

    public String getPoster_path(){
        return poster_path;
    }
}
