package com.example.megha.movieplate.TVFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

public class TVShowDetails extends AppCompatActivity {

    ImageView iv;
    TextView title, overview, releaseDate, language, popularity, voteCount, voteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("TVList Show Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);
        iv = (ImageView) findViewById(R.id.TVShowPosterSearch);
        title = (TextView) findViewById(R.id.id_TVShowTitle);
        overview = (TextView) findViewById(R.id.TvShowOverview);
        releaseDate = (TextView) findViewById(R.id.TvShowReleaseDate);
        language = (TextView) findViewById(R.id.TvShowLanguage);
        popularity = (TextView) findViewById(R.id.TvShowPopularity);
        voteCount = (TextView) findViewById(R.id.TvShowVoteCount);
        voteAverage = (TextView) findViewById(R.id.TvShowVoteAverage);

        Intent intent = getIntent();
        TVDetails result = (TVDetails) intent.getSerializableExtra(Constants.SINGLE_TV_SHOW_DETAILS);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/"+result.getPoster_path()).into(iv);
        title.setText(result.name);
        overview.setText(result.overview);
        releaseDate.setText(result.first_air_date);
        language.setText(result.original_language);
        popularity.setText(result.popularity);
        voteCount.setText(result.vote_count);
        voteAverage.setText(result.vote_average);
    }
}
