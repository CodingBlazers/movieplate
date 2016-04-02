package com.example.megha.movieplate.CelebsFormat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class Results {

    String profile_path;
    int id;
    String name;
    @SerializedName("known_for")
    ArrayList<Popularity> MoviesList;


    public String getProfile_path() {
        return profile_path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
