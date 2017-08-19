package com.example.megha.movieplate.MovieFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Megha on 02-04-2016.
 */
public class SingleMovieActivity extends AppCompatActivity {

    ImageView iv;
    TextView adult, overview, release_date, original_language, title, vote_average, popularity, vote_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("MovieList Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Intent i = getIntent();
        MovieDetails movieDetails = (MovieDetails) i.getSerializableExtra(Constants.SINGLE_MOVIE_DETAILS);
        iv = (ImageView) findViewById(R.id.nPosterSearch);
        overview = (TextView) findViewById(R.id.aOverview);
        release_date = (TextView) findViewById(R.id.aReleaseDate);
        title = (TextView) findViewById(R.id.aMovieTitle);
        original_language = (TextView) findViewById(R.id.aLanguage);
        popularity = (TextView) findViewById(R.id.aPopularity);
        vote_count = (TextView) findViewById(R.id.aVoteCount);
        vote_average = (TextView) findViewById(R.id.aVoteAverage);
        adult = (TextView) findViewById(R.id.aAdult);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/" + movieDetails.getPoster_path()).into(iv);
        overview.setText(movieDetails.overview);
        release_date.setText(movieDetails.release_date);
        title.setText(movieDetails.title);
        original_language.setText(movieDetails.original_language);
        popularity.setText(movieDetails.popularity);
        vote_count.setText(movieDetails.vote_count);
        vote_average.setText(movieDetails.vote_average);
        adult.setText(movieDetails.adult);
    }
}
