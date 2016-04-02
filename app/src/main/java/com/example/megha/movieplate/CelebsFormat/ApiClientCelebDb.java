package com.example.megha.movieplate.CelebsFormat;

import com.example.megha.movieplate.MovieFormat.ApiInterfaceMoviedb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HIman$hu on 4/2/2016.
 */
public class ApiClientCelebDb {
    private static ApiInterfaceCelebDb mService;

    public static ApiInterfaceCelebDb getInterface() {
        if (mService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mService = retrofit.create(ApiInterfaceCelebDb.class);
        }
        return mService;
    }
}
