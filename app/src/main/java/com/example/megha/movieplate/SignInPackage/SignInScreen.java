package com.example.megha.movieplate.SignInPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.HomeActivity;
import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {
    String token;
    String session_id;
    Button logInButton;
    String UserName, api_key;
    EditText username_edit_text, password_edit_text;
    boolean requestTokenGrant = false;
    boolean requestTokenVerify = false, stopped = false;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    Call<account_access> request_token;
    Call<account_access> authenticate;
    Call<session_id> session_idCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);

        stopped = false;

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(this, NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        logInButton = (Button) findViewById(R.id.login_button);
        username_edit_text = (EditText) findViewById(R.id.username);
        password_edit_text = (EditText) findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");

        sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sp.edit();
        api_key = sp.getString(Constants.API_KEY, null);
        request_token = ApiClientMoviedb.getInterface().getRequestToken(api_key);

    }

    @Override
    protected void onResume() {
        stopped = false;
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
                progressDialog.show();
                final String username = username_edit_text.getText().toString();
                final String password = password_edit_text.getText().toString();
                while (!requestTokenGrant){}
                authenticate = ApiClientMoviedb.getInterface().getRequestAuthenticated(api_key, token, username, password);
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
                            session_idCall = ApiClientMoviedb.getInterface().getSessionID(api_key, token);
                            session_idCall.enqueue(new Callback<session_id>() {
                                @Override
                                public void onResponse(Call<session_id> call, Response<session_id> response) {
                                    if (response.isSuccessful()) {
                                        progressDialog.dismiss();
                                        session_id session_idCall1 = response.body();
                                        session_id = session_idCall1.session_id;
                                        editor.putString(Constants.SESSION_ID_SP, session_id);
                                        editor.commit();
                                        editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, true);
                                        editor.commit();
                                        editor.putBoolean(Constants.BOOLEAN_SIGNED_IN, true);
                                        editor.commit();
                                        Log.i("Session id: ", "verified" + session_id);
                                        getAccountSignInDetails();
                                        Intent i = new Intent();
                                        i.setClass(SignInScreen.this, HomeActivity.class);
                                        if(!stopped)
                                            startActivity(i);
                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("Session Id Request", "Session Id can't be granted " + response.errorBody());
                                        editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, false);
                                        editor.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<session_id> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Log.i("Session Id Request", "Session Id can't be granted ");
                                    editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED, false);
                                    editor.commit();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Log.i("Request Token Vrfctn", "Request Token can't be verified " + response.errorBody());
                            editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                            editor.commit();
                            requestTokenVerify = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<account_access> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.i("Request Token Vrfctn", "Network Error: request token can't be verified");
                        editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_VERIFIED, false);
                        editor.commit();
                        requestTokenVerify = false;
                    }
                });
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        stopped = true;
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        request_token.cancel();
        if(authenticate != null)    authenticate.cancel();
        if(session_idCall != null)  session_idCall.cancel();
        super.onPause();
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
                    Log.i("UserNameInSignInScreen", accountDetails.getUsername());
                    Log.i("UserId", accountDetails.getId());
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
