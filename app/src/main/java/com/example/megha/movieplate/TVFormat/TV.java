package com.example.megha.movieplate.TVFormat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class TV implements Serializable {
    public int page;
    @SerializedName("results")
    public ArrayList<Results> TVShowresults;
}
