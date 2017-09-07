package com.example.megha.movieplate.CelebsFormat;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CelebrityDetailsActivity extends AppCompatActivity {

    public static final String TAG = "CelebrityDetailActivity";

    TextView celebName, birthDate, birthPlace;
    Spinner spinner;

    Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    Call<CelebsDetails> celebsDetailsCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String id = (String) i.getSerializableExtra("Profile_ID");
        Log.i(TAG, "UserId: " + String.valueOf(id));

        initFields();

        celebsDetailsCall = MovieDBApiClient.getInterface().getPersonDetails(id, BuildConfig.MOVIE_DB_API_KEY);
        celebsDetailsCall.enqueue(new Callback<CelebsDetails>() {
            @Override
            public void onResponse(Call<CelebsDetails> call, Response<CelebsDetails> response) {
                if (response.isSuccessful()) {
                    responseSuccessful(response.body());
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

    private void responseSuccessful(CelebsDetails celebsDetails) {

        final ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w300/" + celebsDetails.getProfile_path()).into(image);

        celebName.setText(celebsDetails.getName());
        birthDate.setText(celebsDetails.getBirthday());
        birthPlace.setText(celebsDetails.getPlace_of_birth());

        collapsingToolbarLayout.setTitle(celebsDetails.getName());

        final List<String> list = new ArrayList<String>();
        list.add(celebsDetails.getBiography());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CelebrityDetailsActivity.this, android.R.layout.simple_spinner_item, list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(CelebrityDetailsActivity.this);
                }

                final TextView textView = (TextView) convertView;
                textView.setText(list.get(position).toString());
                textView.setMinimumHeight(300);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSingleLine(false);
                    }
                });
                return textView;
            }
        };
        spinner.setAdapter(dataAdapter);
    }


    private void initFields(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        celebName = (TextView) findViewById(R.id.celeb_name);
        birthDate = (TextView) findViewById(R.id.born_date);
        birthPlace = (TextView) findViewById(R.id.birth_place);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

}
