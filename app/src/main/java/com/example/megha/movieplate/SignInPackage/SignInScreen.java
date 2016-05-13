package com.example.megha.movieplate.SignInPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {
    String token;
    String session_id;
    Button logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);
        logInButton = (Button) findViewById(R.id.login_button);
        final EditText username_edittext = (EditText) findViewById(R.id.username);
        final EditText password_edittext = (EditText) findViewById(R.id.password);
        SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
        final String api_key = sp.getString(Constants.API_KEY, null);
        Call<account_access> request_token = ApiClientMoviedb.getInterface().getRequestToken(api_key);
        request_token.enqueue(new Callback<account_access>() {
            @Override
            public void onResponse(Call<account_access> call, Response<account_access> response) {
                account_access account_access_token = response.body();
                token = account_access_token.request_token;
                Log.i("Request Token", token);
            }

            @Override
            public void onFailure(Call<account_access> call, Throwable t) {
                Log.i("Request Token", "Error");
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_edittext.getText().toString();
                final String password = password_edittext.getText().toString();
                Call<account_access> authenticate = ApiClientMoviedb.getInterface().getRequestAuthenticated(api_key,token,username,password);
                authenticate.enqueue(new Callback<account_access>() {
                    @Override
                    public void onResponse(Call<account_access> call, Response<account_access> response) {
                        Log.i("Request Token", response.body().request_token);
                    }

                    @Override
                    public void onFailure(Call<account_access> call, Throwable t) {
                        Log.i("Request Token", "Error no token");
                    }
                });
                final Call<session_id> session_idCall=ApiClientMoviedb.getInterface().getSessionID(api_key,token);
                session_idCall.enqueue(new Callback<session_id>() {
                    @Override
                    public void onResponse(Call<session_id> call, Response<session_id> response) {
                        session_id session_idCall1=response.body();
                        session_id=session_idCall1.session_id;
                        Toast.makeText(SignInScreen.this, session_id, Toast.LENGTH_LONG).show();
                        /////STORE IN SHARED PREFFERNCES//////////////////////////
                        Log.i("Request Token", response.body().session_id);
                    }

                    @Override
                    public void onFailure(Call<session_id> call, Throwable t) {
                        Log.i("Request Token", "Error no session id");
                    }
                });
            }
        });
    }
}
