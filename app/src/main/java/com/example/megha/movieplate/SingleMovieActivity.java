package com.example.megha.movieplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.MovieFormat.Results;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * Created by Megha on 02-04-2016.
 */
public class SingleMovieActivity extends AppCompatActivity {

    ImageView iv;
    TextView adult, overview, release_date, original_language, title, vote_average, popularity, vote_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Intent i = getIntent();
        Results results = (Results) i.getSerializableExtra(Constants.SINGLE_MOVIE_DETAILS);
        iv = (ImageView) findViewById(R.id.nPosterSearch);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/" + results.getPoster_path()).into(iv);
        overview = (TextView) findViewById(R.id.aOverview);
        release_date = (TextView) findViewById(R.id.aReleaseDate);
        title = (TextView) findViewById(R.id.aMovieTitle);
        original_language = (TextView) findViewById(R.id.aLanguage);
        popularity = (TextView) findViewById(R.id.aPopularity);
        vote_count = (TextView) findViewById(R.id.aVoteCount);
        vote_average = (TextView) findViewById(R.id.aVoteAverage);
        adult = (TextView) findViewById(R.id.aAdult);

        overview.setText(results.overview);
        release_date.setText(results.release_date);
        title.setText(results.title);
        original_language.setText(results.original_language);
        popularity.setText(results.popularity);
        vote_count.setText(results.vote_count);
        vote_average.setText(results.vote_average);
        adult.setText(results.adult);
    }
}
