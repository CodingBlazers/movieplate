package com.example.megha.movieplate.TVFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HIman$hu on 3/31/2016.
 */
public class ApiClientTVDb {
    private static ApiInterfaceTVdb mService;

    public static ApiInterfaceTVdb getInterface(){
        Gson gson=new GsonBuilder().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/").addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mService=retrofit.create(ApiInterfaceTVdb.class);
        return mService;
    }
}
