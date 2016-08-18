package com.example.megha.movieplate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.megha.movieplate.SignInPackage.SignInScreen;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//This is to hide notification bar from the splash screen
        final SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        boolean api_key_present = sp.getBoolean(Constants.BOOLEAN_API_KEY_PRESENT, false);
        if (! api_key_present){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constants.API_KEY, "4b21312cf568464ee6b05097ebc6f824");
            editor.commit();
        }

        //In manifest hide the action bar from the main activity so that splash screen appears properly
        final int SPLASH_TIME_OUT=3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                boolean signedIn = sp.getBoolean(Constants.BOOLEAN_SIGNED_IN, false);
                //If already signed in than open the HomeActivity else open the SignInScreen
                if(signedIn){
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setClass(MainActivity.this, HomeActivity.class);

                }
                else{
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setClass(MainActivity.this, SignInScreen.class);

                }
                startActivity(intent);

                overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}