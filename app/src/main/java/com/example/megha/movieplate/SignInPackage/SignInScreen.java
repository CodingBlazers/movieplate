package com.example.megha.movieplate.SignInPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.HomeActivity;
import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {
    String token;
    String session_id;
    Button logInButton;
    String UserName;
    EditText username_edit_text, password_edit_text;
    boolean requestTokenGrant = false;
    boolean requestTokenVerify = false;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);
        logInButton = (Button) findViewById(R.id.login_button);
        username_edit_text = (EditText) findViewById(R.id.username);
        password_edit_text = (EditText) findViewById(R.id.password);

        sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final String api_key = sp.getString(Constants.API_KEY, null);
        //Log.i("Api Key",api_key);
        final Call<account_access> request_token = ApiClientMoviedb.getInterface().getRequestToken(api_key);
        request_token.enqueue(new Callback<account_access>() {
            @Override
            public void onResponse(Call<account_access> call, Response<account_access> response) {
                if (response.isSuccessful()) {
                    account_access account_access_token = response.body();
                    token = account_access_token.request_token;
                    editor.putString(Constants.ACCESS_TOKEN_SP, token);
                    editor.commit();
                    editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, true);
                    editor.commit();
                    Log.i("Request Token Grant", "request token is granted");
                    requestTokenGrant = true;
                } else {
                    Log.i("Request Token Grant", "Request Token Request Unsuccessful");
                    editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, false);
                    editor.commit();
                    requestTokenGrant = false;
                }
            }

            @Override
            public void onFailure(Call<account_access> call, Throwable t) {
                Toast.makeText(SignInScreen.this,"You are not connected to internet",Toast.LENGTH_SHORT).show();
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_edit_text.getText().toString();
                final String password = password_edit_text.getText().toString();
                if (requestTokenGrant) {
                    Call<account_access> authenticate = ApiClientMoviedb.getInterface().getRequestAuthenticated(api_key, token, username, password);
                    authenticate.enqueue(new Callback<account_access>() {
                        @Override
                        public void onResponse(Call<account_access> call, Response<account_access> response) {
                            if (response.isSuccessful()) {
                                requestTokenVerify = true;
                                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, true);
                                editor.commit();
                                Toast.makeText(SignInScreen.this, "Request token Verfied", Toast.LENGTH_SHORT);
                                Log.i("Request Token Verified", "Following request token is verified: " + response.body().request_token);

                                // Since request token has been verified. Call for session id:
                                final Call<session_id> session_idCall = ApiClientMoviedb.getInterface().getSessionID(api_key, token);
                                session_idCall.enqueue(new Callback<session_id>() {
                                    @Override
                                    public void onResponse(Call<session_id> call, Response<session_id> response) {
                                        if (response.isSuccessful()) {
                                            session_id session_idCall1 = response.body();
                                            session_id = session_idCall1.session_id;
                                            editor.putString(Constants.SESSION_ID_SP, session_id);
                                            editor.commit();
                                            editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, true);
                                            editor.commit();
                                            editor.putBoolean(Constants.BOOLEAN_SIGNED_IN, true);
                                            editor.commit();
                                            Toast.makeText(SignInScreen.this, "Signed In", Toast.LENGTH_SHORT);
                                            Log.i("Session id: ", "verified" + session_id);
                                            getAccountSignInDetails();
                                            Intent i = new Intent();
                                            i.setClass(SignInScreen.this, HomeActivity.class);
                                            startActivity(i);
                                        } else {
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
                            } else {
                                Log.i("Request Token Vrfctn", "Request Token can't be verified " + response.errorBody());
                                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                                editor.commit();
                                requestTokenVerify = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<account_access> call, Throwable t) {
                            Log.i("Request Token Vrfctn", "Network Error: request token can't be verified");
                            editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                            editor.commit();
                            requestTokenVerify = false;
                        }
                    });
                } else {
                    // case: Request Token must not have been granted
                    // Handle the case here
                    // Can't get session id on button click
                }
            }
        });
    }

    public void getAccountSignInDetails() {
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String key = sp.getString(Constants.API_KEY, null);
        final SharedPreferences.Editor editor = sp.edit();
        String sessionId = sp.getString(Constants.SESSION_ID_SP, null);
        Call<AccountDetails> details = ApiClientAccountDetails.getInterface().getAccountDetails(key, sessionId);
        details.enqueue(new Callback<AccountDetails>() {
            @Override
            public void onResponse(Call<AccountDetails> call, Response<AccountDetails> response) {
                if (response.isSuccessful()) {
                    AccountDetails accountDetails = response.body();
                    editor.putString(Constants.ID_SP, accountDetails.getId());
                    editor.commit();
                    editor.putBoolean(Constants.BOOLEAN_GET_ACCOUNT_ID, true);
                    editor.commit();
                    editor.putString(Constants.USER_NAME, accountDetails.getUsername());
                    editor.commit();
                    Log.i("Session id: ", "user id granted");
                    Log.i("UserNameInSignInScreen",accountDetails.getUsername());
                    Log.i("UserId",accountDetails.getId());
                } else {
                    editor.putBoolean(Constants.BOOLEAN_GET_ACCOUNT_ID, false);
                    editor.commit();
                    Log.i("Session id: ", "user id not granted " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AccountDetails> call, Throwable t) {
                Toast.makeText(SignInScreen.this,"You are not connected to internet",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
