package com.example.megha.movieplate.TVFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.ApiClientOmdb;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.Search;
import com.example.megha.movieplate.SearchFragment;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetails extends AppCompatActivity {

    ImageView iv;
    TextView title, overview, releaseDate, language, popularity, voteCount, voteAverage;
    ProgressDialog progressDialog;
    Call<Search> tvShowResults;
    Results results;
    boolean paused = false;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("TV Show Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_container);

        toolbar = (Toolbar) findViewById(R.id.toolbar_details);

        Intent intent = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        results = (Results) intent.getSerializableExtra(Constants.SINGLE_TV_SHOW_DETAILS);
    }

    @Override
    protected void onResume() {

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(this, NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        paused = false;
        progressDialog.show();
        String tv_show_title = results.getName();
        toolbar.setTitle(tv_show_title);
        tvShowResults = ApiClientOmdb.getInterface().getMySearch("", tv_show_title);
        tvShowResults.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Search search = response.body();
                    SearchFragment searchFragment = new SearchFragment();
                    Bundle b = new Bundle();
                    b.putSerializable("SearchContent",search);
                    searchFragment.setArguments(b);
                    if(!paused)
                        getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivityContainer,searchFragment).commit();
                }
                else{
                    Toast.makeText(TVShowDetails.this, response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TVShowDetails.this, "You are not connected to Internet" , Toast.LENGTH_LONG).show();
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        paused = true;
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        tvShowResults.cancel();
        super.onPause();
    }
}
