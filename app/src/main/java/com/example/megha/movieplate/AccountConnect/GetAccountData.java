package com.example.megha.movieplate.AccountConnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.megha.movieplate.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Megha on 13-05-2016.
 */
public class GetAccountData extends AppCompatActivity {

    public static boolean detailsTaken = false;
    public void getAccountDetails(){

        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERNCE, Context.MODE_PRIVATE);
        String key = sp.getString(Constants.API_KEY, null);
        final SharedPreferences.Editor editor = sp.edit();
        String sessionId = sp.getString(Constants.SESSION_ID_SP, null);
        Call<AccountDetails> details = ApiClientAccountDetails.getInterface().getAccountDetails(key, sessionId);
        details.enqueue(new Callback<AccountDetails>() {
            @Override
            public void onResponse(Call<AccountDetails> call, Response<AccountDetails> response) {
                if (response.isSuccessful()) {
                    editor.putString(Constants.ID_SP, response.body().id);
                    editor.commit();
                    detailsTaken = true;
                } else {
                    detailsTaken = false;
                }
            }

            @Override
            public void onFailure(Call<AccountDetails> call, Throwable t) {
                detailsTaken = false;
            }
        });
    }
}
