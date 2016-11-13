package com.example.megha.movieplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.megha.movieplate.utility.ConnectionDetector;

/**
 * Created by megha on 13/11/16.
 */

public class NoInternetActivity extends AppCompatActivity {

    Button retryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(NoInternetActivity.this);
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent();
                    intent.setClass(NoInternetActivity.this, HomeActivity.class);
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
