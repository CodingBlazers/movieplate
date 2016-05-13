package com.example.megha.movieplate.AccountConnect;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Megha on 13-05-2016.
 */
public interface ApiInterfaceAccountDetails {
    @GET("/account")
    Call<AccountDetails> getAccountDetails(@Query("api_key") String key, @Query("session_id") String session_id);
}
