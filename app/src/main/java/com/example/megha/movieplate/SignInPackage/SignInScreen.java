package com.example.megha.movieplate.SignInPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.HomeActivity;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {

    public static final String TAG = "SignInScreen";

    String token;
    String session_id;
    Button logInButton;
    EditText username_edit_text, password_edit_text;
    boolean requestTokenGrant = false;
    boolean requestTokenVerify = false, stopped = false;

    SharedPreferencesUtils spUtils;

    ProgressDialog progressDialog;

    Call<account_access> request_token;
    Call<account_access> authenticate;
    Call<session_id> session_idCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);

        stopped = false;

        checkConnectivity();
        initViews();

        request_token = MovieDBApiClient.getInterface().getRequestToken(BuildConfig.MOVIE_DB_API_KEY);

    }

    private void initViews() {
        logInButton = (Button) findViewById(R.id.login_button);
        username_edit_text = (EditText) findViewById(R.id.username);
        password_edit_text = (EditText) findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");
        spUtils = new SharedPreferencesUtils(this);
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
                    spUtils.setAccessToken(token);
                    Log.i(TAG, "request token is granted");
                    requestTokenGrant = true;
                } else {
                    Log.i(TAG, "Request Token Request Unsuccessful");
                    spUtils.setAccessTokenFalse();
                    requestTokenGrant = false;
                }
            }

            @Override
            public void onFailure(Call<account_access> call, Throwable t) {
                Toast.makeText(SignInScreen.this, "You are not connected to internet", Toast.LENGTH_SHORT).show();
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String username = username_edit_text.getText().toString();
                final String password = password_edit_text.getText().toString();
                while (!requestTokenGrant){}
                authenticate = MovieDBApiClient.getInterface().getRequestAuthenticated(BuildConfig.MOVIE_DB_API_KEY, token, username, password);
                authenticate.enqueue(new Callback<account_access>() {
                    @Override
                    public void onResponse(Call<account_access> call, Response<account_access> response) {
                        if (response.isSuccessful()) {
                            requestTokenVerify = true;
                            spUtils.setAccessTokenVerified();
                            Toast.makeText(SignInScreen.this, "Request token Verified", Toast.LENGTH_SHORT);
                            Log.i(TAG, "Following request token is verified: " + response.body().request_token);

                            // Since request token has been verified. Call for session id:
                            session_idCall = MovieDBApiClient.getInterface().getSessionID(BuildConfig.MOVIE_DB_API_KEY, token);
                            session_idCall.enqueue(new Callback<session_id>() {
                                @Override
                                public void onResponse(Call<session_id> call, Response<session_id> response) {
                                    if (response.isSuccessful()) {
                                        progressDialog.dismiss();
                                        session_id session_idCall1 = response.body();
                                        session_id = session_idCall1.session_id;
                                        spUtils.setSessionID(session_id);
                                        Log.i(TAG, "Session ID verified: " + session_id);
                                        getAccountSignInDetails();
                                        Intent i = new Intent();
                                        i.setClass(SignInScreen.this, HomeActivity.class);
                                        if(!stopped)
                                            startActivity(i);
                                    } else {
                                        progressDialog.dismiss();
                                        Log.i(TAG, "Session Id can't be granted " + response.errorBody());
                                        spUtils.setSessionIDGrantedFalse();
                                    }
                                }

                                @Override
                                public void onFailure(Call<session_id> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Log.i(TAG, "Session Id can't be granted ");
                                    spUtils.setSessionIDGrantedFalse();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Log.i(TAG, "Request Token can't be verified " + response.errorBody());
                            spUtils.setAccessTokenFalse();
                            requestTokenVerify = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<account_access> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.i(TAG, "Network Error: request token can't be verified");
                        spUtils.setAccessTokenVerifiedFalse();
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
        String sessionId = spUtils.getSessionIDKey();
        Call<AccountDetails> details = MovieDBApiClient.getInterface().getAccountDetails(BuildConfig.MOVIE_DB_API_KEY, sessionId);
        details.enqueue(new Callback<AccountDetails>() {
            @Override
            public void onResponse(Call<AccountDetails> call, Response<AccountDetails> response) {
                if (response.isSuccessful()) {
                    AccountDetails accountDetails = response.body();
                    spUtils.setAccountDetails(accountDetails.getId(), accountDetails.getUsername());
                    Log.i(TAG, "Session id: user id granted");
                    Log.i(TAG, "Username: " + accountDetails.getUsername());
                    Log.i(TAG, "User ID: " + accountDetails.getId());
                } else {
                    spUtils.setAccountIDFalse();
                    Log.i(TAG, "user id not granted " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AccountDetails> call, Throwable t) {
                Toast.makeText(SignInScreen.this, "You are not connected to internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkConnectivity() {
        ConnectionDetector cd = new ConnectionDetector(SignInScreen.this);
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(this, NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
