package com.example.megha.movieplate.utility.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by megha on 14/08/17.
 */

public class MovieDBApiClient {

    private static MovieDBApiInterface mService;

    public static MovieDBApiInterface getInterface() {
        if (mService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mService = retrofit.create(MovieDBApiInterface.class);
        }
        return mService;
    }

}
