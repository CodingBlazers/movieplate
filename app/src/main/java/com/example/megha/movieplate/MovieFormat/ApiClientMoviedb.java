package com.example.megha.movieplate.MovieFormat;

import com.example.megha.movieplate.MovieFormat.ApiInterfaceMoviedb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Megha on 31-03-2016.
 */
public class ApiClientMoviedb {
    private static ApiInterfaceMoviedb mService;

    public static ApiInterfaceMoviedb getInterface() {
        if (mService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mService = retrofit.create(ApiInterfaceMoviedb.class);
        }
        return mService;
    }

}
