package com.example.megha.movieplate;

import android.support.v4.app.Fragment;
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
    TextView movieTitle, year, releaseDate, genre, duration, plot, director, writer, actors, language, imdbRating, metaScore;
    ImageView poster;
    Search s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search_fragment, container, false);
        poster = (ImageView) v.findViewById(R.id.posterSearch);
        movieTitle = (TextView) v.findViewById(R.id.movieTitleSearch);
        year = (TextView) v.findViewById(R.id.movieYear);
        genre = (TextView) v.findViewById(R.id.genreSearch);
        duration = (TextView) v.findViewById(R.id.durationSearch);
        plot = (TextView) v.findViewById(R.id.plotSearch);
        imdbRating = (TextView) v.findViewById(R.id.imdbRatingSearch);
        releaseDate = (TextView) v.findViewById(R.id.releaseDateSearch);
        actors = (TextView) v.findViewById(R.id.actorsSearch);
        director = (TextView) v.findViewById(R.id.directorSearch);
        writer = (TextView) v.findViewById(R.id.writerSearch);
        language = (TextView) v.findViewById(R.id.languageSearch);
        metaScore = (TextView) v.findViewById(R.id.metaScoreSearch);

        Bundle b = getArguments();
        if (b != null) {
            s = (Search) b.getSerializable("searchMovie");
            if (s != null) {
                Picasso.with(getActivity()).load(s.Poster).into(poster);
                movieTitle.setText(s.title);
                year.setText("(" + s.Year + ")");
                genre.setText(s.genre);
                duration.setText(s.duration);
                plot.setText(s.plot);
                imdbRating.setText(s.imdbRating+"/10");
                releaseDate.setText(s.releaseDate);
                actors.setText(s.actors);
                director.setText(s.director);
                writer.setText(s.writer);
                language.setText(s.language);
                metaScore.setText(s.metaScore);
            }
        }
        return v;
    }
}

