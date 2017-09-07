package com.example.megha.movieplate.TVFormat;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.WatchlistFormat.PostJsonInWatchList;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by megha on 06/09/17.
 */

public class SingleTVShowCardView   extends Fragment implements Constants {

    View rootView;
    TextView title, releaseDate, rating;
    ImageView addToWatchList, poster;

    SharedPreferencesUtils spUtils;
    String userID, sessionID;

    boolean added;

    TVDetails mTvDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.single_entity_in_list, container, false);
        spUtils = new SharedPreferencesUtils(getContext());
        userID = spUtils.getIDKey();
        sessionID = spUtils.getSessionIDKey();
        initializeViews();

        Bundle b = getArguments();
        mTvDetails = (TVDetails) b.get(SINGLE_TV_SHOW_DETAILS);

        setViewsData();

        setWatchListListener();

        return rootView;

    }

    private void setWatchListListener() {
        addToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWatchList.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) addToWatchList.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!added) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", mTvDetails.getId(), true);
                    Call<PostJsonInWatchList> call = MovieDBApiClient.getInterface().createJson(userID, BuildConfig.MOVIE_DB_API_KEY, sessionID, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TVList Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", mTvDetails.getId(), false);
                    Call<PostJsonInWatchList> call = MovieDBApiClient.getInterface().createJson(userID, BuildConfig.MOVIE_DB_API_KEY, sessionID, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TVList Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                added = !added;
            }
        });
    }

    private void setViewsData() {
        title.setText(mTvDetails.getName());
        releaseDate.setText(mTvDetails.getFirstAirDate());
        // rating.setText(movieDetails.getRating());
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + mTvDetails.getPosterPath()).into(poster);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TVShowDetails.class);
                intent.putExtra(SINGLE_TV_SHOW_DETAILS, mTvDetails);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        title = (TextView) rootView.findViewById(R.id.title_text_view);
        releaseDate = (TextView) rootView.findViewById(R.id.release_date_text_view);
        rating = (TextView) rootView.findViewById(R.id.rating_text_view);
        addToWatchList = (ImageView) rootView.findViewById(R.id.watchlist_image_view);
        poster = (ImageView) rootView.findViewById(R.id.poster_image_view);
    }

}
