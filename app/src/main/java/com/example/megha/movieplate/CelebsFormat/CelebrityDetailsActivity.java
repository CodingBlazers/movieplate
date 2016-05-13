package com.example.megha.movieplate.CelebsFormat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CelebrityDetailsActivity extends AppCompatActivity {


    ImageView imageView;
    TextView CelebName, BirthDate, BirthPlace, Biography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_details);
        Intent i = getIntent();
        String key = (String) i.getSerializableExtra(Constants.CELEBS_URL_API_KEY);
        Log.i("ApiKey", key);
        String id = (String) i.getSerializableExtra("Profile_ID");
        Log.i("UserId", String.valueOf(id));
        imageView = (ImageView) findViewById(R.id.Celeb_Poster);
        CelebName = (TextView) findViewById(R.id.Celeb_Name);
        BirthDate = (TextView) findViewById(R.id.Born_date);
        BirthPlace = (TextView) findViewById(R.id.Birth_Place);
        Biography = (TextView) findViewById(R.id.Biography);
        Call<CelebsDetails> celebsDetails = ApiClientCelebDb.getInterface().getPersonDetails(id, key);
        celebsDetails.enqueue(new Callback<CelebsDetails>() {
            @Override
            public void onResponse(Call<CelebsDetails> call, Response<CelebsDetails> response) {
                if (response.isSuccessful()) {
                    CelebsDetails celebsDetails1 = response.body();
                    Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w300/" + celebsDetails1.getProfile_path()).into(imageView);
                    CelebName.setText(celebsDetails1.getName());
                    BirthDate.setText(celebsDetails1.getBirthday());
                    BirthPlace.setText(celebsDetails1.getPlace_of_birth());
                    Biography.setText(celebsDetails1.getBiography());
                } else {
                    Toast.makeText(getBaseContext(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CelebsDetails> call, Throwable t) {
                Toast.makeText(getBaseContext(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
