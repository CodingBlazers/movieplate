package com.example.megha.movieplate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.megha.movieplate.SignInPackage.SignInScreen;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);
        final SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_PRIVATE);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                boolean signedIn = sp.getBoolean(Constants.BOOLEAN_SIGNED_IN, false);
                if(signedIn){
                    i.setClass(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    i.setClass(MainActivity.this, SignInScreen.class);
                    startActivity(i);
                }
            }
        });
    }
}
