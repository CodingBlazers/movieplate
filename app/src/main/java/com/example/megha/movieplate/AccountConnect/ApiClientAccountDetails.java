package com.example.megha.movieplate.AccountConnect;

import com.example.megha.movieplate.MovieFormat.ApiInterfaceMoviedb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Megha on 13-05-2016.
 */
public class ApiClientAccountDetails {
    private static ApiInterfaceAccountDetails mService;

    public static ApiInterfaceAccountDetails getInterface() {
        if (mService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mService = retrofit.create(ApiInterfaceAccountDetails.class);
        }
        return mService;
    }
}
