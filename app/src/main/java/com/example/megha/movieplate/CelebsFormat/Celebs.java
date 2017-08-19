package com.example.megha.movieplate.CelebsFormat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class Celebs implements Serializable{

    int page;
    @SerializedName("results")
    ArrayList<Results> celebsList;

}
