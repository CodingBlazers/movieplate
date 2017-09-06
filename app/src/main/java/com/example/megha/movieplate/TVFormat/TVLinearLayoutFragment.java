package com.example.megha.movieplate.TVFormat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.WatchlistFormat.PostJsonInWatchList;
import com.example.megha.movieplate.utility.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;
import com.example.megha.movieplate.utility.UiUnitConverter;
import com.example.megha.movieplate.utility.ViewIdGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by megha on 14/08/17.
 */

public class TVLinearLayoutFragment extends Fragment implements Constants{

    Context mContext;
    View view;

    boolean state[];

    ImageView posterView[];
    ImageView addToWatchlist[];

    ArrayList<TVDetails> mData;
    String key, userID, sessionID;

    SharedPreferencesUtils spUtils;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle b = getArguments();
        mData = (ArrayList<TVDetails>) b.getSerializable(ALL_TV_SHOW_DETAILS);
        posterView = new ImageView[10];
        addToWatchlist = new ImageView[10];
        state = new boolean[10];
        mContext = getContext();

        spUtils = new SharedPreferencesUtils(getContext());
        key = spUtils.getAPIKey();
        userID = spUtils.getIDKey();
        sessionID = spUtils.getSessionIDKey();

        initViews();
        return view;
    }

    private void initViews() {

        final Intent intent = new Intent();
        intent.setClass(getActivity(), TVShowDetails.class);

        HorizontalScrollView scrollView = new HorizontalScrollView(mContext);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollView.addView(linearLayout);

        for(int i=0; i<10; i++){
            RelativeLayout relativeLayout = new RelativeLayout(mContext);
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            posterView[i] = new ImageView(mContext);
            posterView[i].setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            posterView[i].setId(ViewIdGenerator.generateViewId());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + mData.get(i).getPoster_path()).resize(400, 600).into(posterView[i]);
            relativeLayout.addView(posterView[i]);

            addToWatchlist[i] = new ImageView(mContext);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(UiUnitConverter.topxConverter(50, TypedValue.COMPLEX_UNIT_DIP),
                    UiUnitConverter.topxConverter(50, TypedValue.COMPLEX_UNIT_DIP));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            addToWatchlist[i].setLayoutParams(params);
            addToWatchlist[i].setAlpha(0.5f);
            addToWatchlist[i].setBackgroundResource(R.drawable.transition);
            addToWatchlist[i].setId(ViewIdGenerator.generateViewId());
            relativeLayout.addView(addToWatchlist[i]);

            TextView titleTextView = new TextView(mContext);
            RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(UiUnitConverter.topxConverter(50, TypedValue.COMPLEX_UNIT_DIP),
                    UiUnitConverter.topxConverter(50, TypedValue.COMPLEX_UNIT_DIP));
            tvParams.addRule(RelativeLayout.ALIGN_LEFT, posterView[i].getId());
            tvParams.addRule(RelativeLayout.ALIGN_RIGHT, posterView[i].getId());
            tvParams.addRule(RelativeLayout.BELOW, posterView[i].getId());
            tvParams.setMargins(0, UiUnitConverter.topxConverter(-10, TypedValue.COMPLEX_UNIT_DIP), 0, 0);
            titleTextView.setLayoutParams(tvParams);
            titleTextView.setText(mData.get(i).getName());
            titleTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            relativeLayout.addView(titleTextView);

            linearLayout.addView(relativeLayout);

            posterView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j;
                    for(j=0; j<9; j++){
                        if(v.getId() == posterView[j].getId())
                            break;
                    }
                    intent.putExtra(SINGLE_TV_SHOW_DETAILS, mData.get(j));
                    startActivity(intent);
                }
            });

            state[i] = spUtils.getState("state" + (i + 1));
            addToWatchlist[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j;
                    for(j=0; j<9; j++){
                        if(v.getId() == addToWatchlist[j].getId())
                            break;
                    }
                    int id = mData.get(j).getId();
                    addToWatchlist[j].setImageDrawable(getResources().getDrawable(R.drawable.transition));
                    TransitionDrawable drawable = (TransitionDrawable) addToWatchlist[j].getDrawable();
                    drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                    if (!state[j]) {
                        drawable.startTransition(100);
                        PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                        Call<PostJsonInWatchList> call = MovieDBApiClient.getInterface().createJson(userID, key, sessionID, postJsonInWatchList);
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
                        PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                        Call<PostJsonInWatchList> call = MovieDBApiClient.getInterface().createJson(userID, key, sessionID, postJsonInWatchList);
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
                    state[j] = !state[j];
                    spUtils.setState("state" + (j + 1), state[j]);

                }
            });

        }

        view = scrollView;

    }

}
