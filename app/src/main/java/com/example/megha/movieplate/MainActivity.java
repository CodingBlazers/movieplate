package com.example.megha.movieplate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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

        final SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
        boolean api_key_present = sp.getBoolean(Constants.BOOLEAN_API_KEY_PRESENT, false);
        if (! api_key_present){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constants.API_KEY, "4b21312cf568464ee6b05097ebc6f824");
            editor.commit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                boolean signedIn = sp.getBoolean(Constants.BOOLEAN_SIGNED_IN, false);
                if(signedIn){
                    intent.setClass(MainActivity.this, HomeActivity.class);

                }
                else{
                    intent.setClass(MainActivity.this, SignInScreen.class);

                }
                startActivity(intent);

                overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
                finish();
            }
        }, 2000);
    }
}