package com.example.megha.movieplate.SignInPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by megha on 23/6/16.
 */
public interface ApiInterfaceAccountDetails {
    @GET("/3/account")
    Call<AccountDetails> getAccountDetails(@Query("api_key") String key, @Query("session_id") String session_id);
}
