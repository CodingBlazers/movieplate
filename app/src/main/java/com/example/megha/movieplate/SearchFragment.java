package com.example.megha.movieplate;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Megha on 27-03-2016.
 */
public class SearchFragment extends Fragment {
    TextView movieTitle, genre, duration, plot, releaseDate, director, writer, actors, language, imdbRating, metaScore;
    ImageView poster;
    Search s;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search_fragment, container, false);
        Bundle b = getArguments();
        s = (Search) b.getSerializable("searchMovie");
        poster = (ImageView) v.findViewById(R.id.posterSearch);
        movieTitle = (TextView) v.findViewById(R.id.movieTitleSearch);
        genre = (TextView) v.findViewById(R.id.genreSearch);
        duration = (TextView) v.findViewById(R.id.durationSearch);
        plot = (TextView) v.findViewById(R.id.plotSearch);
        releaseDate = (TextView) v.findViewById(R.id.releaseDateSearch);
        director = (TextView) v.findViewById(R.id.directorSearch);
        actors = (TextView) v.findViewById(R.id.actorsSearch);
        writer = (TextView) v.findViewById(R.id.writerSearch);
        imdbRating = (TextView) v.findViewById(R.id.imdbRatingSearch);
        language = (TextView) v.findViewById(R.id.languageSearch);
        metaScore = (TextView) v.findViewById(R.id.metaScoreSearch);


//        Picasso.with(getActivity()).load(s.Poster).into(poster);

        movieTitle.setText(s.title);
        genre.setText(s.genre);
        duration.setText(s.duration);
        plot.setText(s.plot);
        releaseDate.setText(s.releaseDate);
        director.setText(s.director);
        actors.setText(s.actors);
        writer.setText(s.writer);
        imdbRating.setText(s.imdbRating);
        language.setText(s.language);
        metaScore.setText(s.metaScore);
        if(b != null){
            Search s = (Search) b.getSerializable("searchMovie");
        }
        return v;
    }
}
