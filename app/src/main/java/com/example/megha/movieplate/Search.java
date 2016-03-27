package com.example.megha.movieplate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Megha on 27-03-2016.
 */
public class Search implements Serializable {
    String Poster;
    @SerializedName("Title")
    String title;
    String Year;
    @SerializedName("Genre")
    String genre;
    @SerializedName("Runtime")
    String duration;
    @SerializedName("Plot")
    String plot;
    @SerializedName("Released")
    String releaseDate;
    @SerializedName("Director")
    String director;
    @SerializedName("Writer")
    String writer;
    @SerializedName("Actors")
    String actors;
    @SerializedName("Language")
    String language;
    @SerializedName("imdbRating")
    String imdbRating;
    @SerializedName("Metascore")
    String metaScore;
}
