package com.example.megha.movieplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.utility.API.ApiClientOmdb;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Megha on 27-03-2016.
 */
public class SearchFragment extends Fragment implements Constants{

    TextView movieTitle, year, releaseDate, genre, duration, plot, director, writer, actors, language, imdbRating,
            metaScore;
    ImageView poster;
    View view;
    ProgressDialog progressDialog;

    Call<Search> mySearch;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search_fragment, container, false);
        initializeViews();
        checkConnectivity();

        Bundle b = getArguments();

        mySearch = ApiClientOmdb.getInterface().getMySearch(Constants.OMDB_API_KEY, b.getString(SEARCH_TITLE));
        mySearch.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                progressDialog.cancel();
                if (response.isSuccessful()) {
                    Search s = response.body();
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
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initializeViews() {
        poster = (ImageView) view.findViewById(R.id.posterSearch);
        movieTitle = (TextView) view.findViewById(R.id.movieTitleSearch);
        year = (TextView) view.findViewById(R.id.movieYear);
        genre = (TextView) view.findViewById(R.id.genreSearch);
        duration = (TextView) view.findViewById(R.id.durationSearch);
        plot = (TextView) view.findViewById(R.id.plotSearch);
        imdbRating = (TextView) view.findViewById(R.id.imdbRatingSearch);
        releaseDate = (TextView) view.findViewById(R.id.releaseDateSearch);
        actors = (TextView) view.findViewById(R.id.actorsSearch);
        director = (TextView) view.findViewById(R.id.directorSearch);
        writer = (TextView) view.findViewById(R.id.writerSearch);
        language = (TextView) view.findViewById(R.id.languageSearch);
        metaScore = (TextView) view.findViewById(R.id.metaScoreSearch);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
    }



    private void checkConnectivity() {
        ConnectionDetector cd = new ConnectionDetector(getContext());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onDetach() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        mySearch.cancel();
        super.onDetach();
    }

}

