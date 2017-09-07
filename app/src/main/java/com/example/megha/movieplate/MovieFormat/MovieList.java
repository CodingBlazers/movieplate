package com.example.megha.movieplate.MovieFormat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Megha on 31-03-2016.
 */
public class MovieList implements Serializable {
    public int page;
    public ArrayList<MovieDetails> results;
    int total_pages;
    int total_results;
}
