package com.example.megha.movieplate.MovieFormat;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Megha on 31-03-2016.
 */
public class Movie implements Serializable {
    public int page;
    public ArrayList<Results> results;
    public Bitmap Mov_img;
}
