package com.example.megha.movieplate.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.HomeActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.SignInPackage.SignInScreen;

/**
 * Created by megha on 13/11/16.
 */

public class NoInternetActivity extends AppCompatActivity {

    Button retryButton;
    SharedPreferencesUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        spUtils = new SharedPreferencesUtils(NoInternetActivity.this);
        retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(NoInternetActivity.this);
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent();
                    intent.setClass(NoInternetActivity.this, HomeActivity.class);
                    if(spUtils.isSignedIn()){
                        intent.setClass(NoInternetActivity.this, HomeActivity.class);
                    }
                    else{
                        intent.setClass(NoInternetActivity.this, SignInScreen.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NoInternetActivity.this, "Sorry no internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
