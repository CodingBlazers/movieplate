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

    ImageView poster, backdropImage;
    TextView adult, overview, release_date, original_language, title, vote_average, popularity, vote_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("MovieList Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Intent i = getIntent();
        MovieDetails movieDetails = (MovieDetails) i.getSerializableExtra(Constants.SINGLE_MOVIE_DETAILS);
        poster = (ImageView) findViewById(R.id.poster_image_view);
        backdropImage = (ImageView) findViewById(R.id.backdrop_image_view);
        overview = (TextView) findViewById(R.id.overview);
        release_date = (TextView) findViewById(R.id.release_date);
        title = (TextView) findViewById(R.id.movie_title);
        original_language = (TextView) findViewById(R.id.language);
        popularity = (TextView) findViewById(R.id.popularity);
        vote_count = (TextView) findViewById(R.id.vote_count);
        vote_average = (TextView) findViewById(R.id.vote_average);
        adult = (TextView) findViewById(R.id.adult);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/" + movieDetails.getPosterPath()).into(poster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/" + movieDetails.getBackdropPath()).into(backdropImage);
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
