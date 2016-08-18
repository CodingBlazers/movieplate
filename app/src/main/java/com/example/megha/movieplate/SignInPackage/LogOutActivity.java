package com.example.megha.movieplate.SignInPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;

/**
 * Created by megha on 1/7/16.
 */
public class LogOutActivity extends AppCompatActivity{

    Button logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logout = (Button) findViewById(R.id.submitButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Constants.BOOLEAN_SIGNED_IN, false);
                editor.commit();
                editor.putBoolean(Constants.BOOLEAN_ACCESS_TOKEN_TAKEN, false);
                editor.commit();
                editor.putBoolean(Constants.BOOLEAN_SESSION_ID_GRANTED,false);
                editor.commit();
                Intent i = new Intent(LogOutActivity.this, SignInScreen.class);
                // set the new task and clear flags
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
