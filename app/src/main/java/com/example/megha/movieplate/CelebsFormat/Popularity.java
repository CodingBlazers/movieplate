package com.example.megha.movieplate.CelebsFormat;

import java.io.Serializable;

/**
 * Created by HIman$hu on 4/2/2016.
 */
public class Popularity implements Serializable{
    String poster_path;
    String overview;
    String release_date;
    String original_title;
    int id;
    String backdrop_path;

    public String getOriginal_title() {
        return original_title;
    }
}
