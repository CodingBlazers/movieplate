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

    ImageView poster, backdropImage;
    TextView title, overview, releaseDate, language, popularity, voteCount, voteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("TVList Show Detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);
        poster = (ImageView) findViewById(R.id.poster_image_view);
        backdropImage = (ImageView) findViewById(R.id.backdrop_image_view);
        title = (TextView) findViewById(R.id.movie_title);
        overview = (TextView) findViewById(R.id.overview);
        releaseDate = (TextView) findViewById(R.id.release_date);
        language = (TextView) findViewById(R.id.language);
        popularity = (TextView) findViewById(R.id.popularity);
        voteCount = (TextView) findViewById(R.id.vote_count);
        voteAverage = (TextView) findViewById(R.id.vote_average);

        Intent intent = getIntent();
        TVDetails result = (TVDetails) intent.getSerializableExtra(Constants.SINGLE_TV_SHOW_DETAILS);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/"+result.getPosterPath()).into(poster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/"+result.getBackdropPath()).into(backdropImage);
        title.setText(result.name);
        overview.setText(result.overview);
        releaseDate.setText(result.first_air_date);
        language.setText(result.original_language);
        popularity.setText(result.popularity);
        voteCount.setText(result.vote_count);
        voteAverage.setText(result.vote_average);
    }
}
