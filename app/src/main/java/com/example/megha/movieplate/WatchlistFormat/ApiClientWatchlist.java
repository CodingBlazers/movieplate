package com.example.megha.movieplate.WatchlistFormat;

import com.example.megha.movieplate.SignInPackage.ApiInterfaceAccountDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by megha on 24/6/16.
 */
public class ApiClientWatchlist {
    private static ApiInterfaceWatchlist apiService;

    public static ApiInterfaceWatchlist getInterface() {
        if (apiService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            apiService = retrofit.create(ApiInterfaceWatchlist.class);
        }
        return apiService;
    }
}
