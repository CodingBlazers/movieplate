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
    boolean requestTokenGrant = false;
    boolean requestTokenVerify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);
        logInButton = (Button) findViewById(R.id.login_button);
        final EditText username_edittext = (EditText) findViewById(R.id.username);
        final EditText password_edittext = (EditText) findViewById(R.id.password);
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final String api_key = sp.getString(Constants.API_KEY, null);
        final Call<account_access> request_token = ApiClientMoviedb.getInterface().getRequestToken(api_key);
        request_token.enqueue(new Callback<account_access>() {
            @Override
            public void onResponse(Call<account_access> call, Response<account_access> response) {
                if(response.isSuccessful()){
                    account_access account_access_token = response.body();
                    token = account_access_token.request_token;
                    editor.putString(Constants.ACCESS_TOKEN_SP, token);
                    editor.commit();
                    editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, true);
                    editor.commit();
                    requestTokenGrant = true;
                    //  Log.i("Request Token Grant", "Requested token granted as:" + token);
                }
                else{
                    Log.i("Request Token Grant", "Request Token Request Unsuccessful");
                    editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, false);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<account_access> call, Throwable t) {
                Log.i("Request Token Grant", "Request Token Grant Error");
                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, false);
                editor.commit();
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_edittext.getText().toString();
                final String password = password_edittext.getText().toString();
                if(requestTokenGrant){
                    Call<account_access> authenticate = ApiClientMoviedb.getInterface().getRequestAuthenticated(api_key, token, username, password);
                    authenticate.enqueue(new Callback<account_access>() {
                        @Override
                        public void onResponse(Call<account_access> call, Response<account_access> response) {
                            if(response.isSuccessful()){
                                requestTokenVerify = true;
                                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, true);
                                editor.commit();
                                //   Log.i("Request Token Verified", "Following request token is verified: " + response.body().request_token);
                            }
                            else {
                                Log.i("Request Token Verification", "Request Token can't be verified " + response.errorBody());
                                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                                editor.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<account_access> call, Throwable t) {
                            Log.i("Request Token Verification", "Network Error: request token can't be verified");
                            editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                            editor.commit();
                        }
                    });
                    if(requestTokenVerify){
                        final Call<session_id> session_idCall = ApiClientMoviedb.getInterface().getSessionID(api_key, token);
                        session_idCall.enqueue(new Callback<session_id>() {
                            @Override
                            public void onResponse(Call<session_id> call, Response<session_id> response) {
                                if(response.isSuccessful()){
                                    session_id session_idCall1 = response.body();
                                    session_id = session_idCall1.session_id;
                                    editor.putString(Constants.SESSION_ID_SP, session_id);
                                    editor.commit();
                                    editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, true);
                                    editor.commit();
                                    editor.putBoolean(Constants.BOOLEAN_SIGNED_IN, true);
                                    editor.commit();
                                    //      Toast.makeText(SignInScreen.this, session_id, Toast.LENGTH_LONG).show();
                                    //      Log.i("Request Token", response.body().session_id);
                                }
                                else {
                                    Log.i("Session Id Request", "Session Id can't be granted " + response.errorBody());
                                    editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, false);
                                    editor.commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<session_id> call, Throwable t) {
                                Log.i("Session Id Request", "Session Id can't be granted ");
                                editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, false);
                                editor.commit();
                            }
                        });
                    }
                    else{
                        // Request token is verified yet due to some error session id isn't created
                        // handle the issue here
                    }
                }
                else{
                    // case: Request Token must not have been granted
                    // Handle the case here
                    // Can't get session id on button click
                }
            }
        });
    }
}
