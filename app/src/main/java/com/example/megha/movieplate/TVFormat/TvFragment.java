package com.example.megha.movieplate.TVFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 3/31/2016.
 */
public class TvFragment extends Fragment {

    Call<TVList> popularTvShows, mostRatedTVShows;
    boolean b1, b2, paused;
    ProgressDialog pDialog;
    SharedPreferencesUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv_fragment, container, false);

        spUtils = new SharedPreferencesUtils(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        paused = b1 = b2 = false;

        return view;
    }

    @Override
    public void onResume() {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        pDialog.show();
        paused = false;
        String key = spUtils.getAPIKey();

        popularTvShows = MovieDBApiClient.getInterface().getPopularTvShows(key);
        popularTvShows.enqueue(new Callback<TVList>() {
            @Override
            public void onResponse(Call<TVList> call, Response<TVList> response) {
                if (response.isSuccessful()) {
                    TVList tvList = response.body();
                    Log.i("TVList Object", tvList.toString());
                    Bundle b = new Bundle();
                    TVLinearLayoutFragment tvf = new TVLinearLayoutFragment();
                    b.putSerializable(Constants.ALL_TV_SHOW_DETAILS, tvList.results);
                    tvf.setArguments(b);
                    if(!paused)
                        getFragmentManager().beginTransaction().replace(R.id.id_PopularTvShows, tvf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TVList> call, Throwable t) {
                // Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }
        });

        mostRatedTVShows = MovieDBApiClient.getInterface().getMostRatedTvShows(key);
        mostRatedTVShows.enqueue(new Callback<TVList>() {
            @Override
            public void onResponse(Call<TVList> call, Response<TVList> response) {
                if (response.isSuccessful()) {
                    TVList tvList = response.body();
                    TVLinearLayoutFragment tvf = new TVLinearLayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.ALL_TV_SHOW_DETAILS, tvList.results);
                    tvf.setArguments(b);
                    if(!paused)
                        getFragmentManager().beginTransaction().replace(R.id.id_MostRatedTvShows, tvf).commit();
                } else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                b2=true;
                if(b1)
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TVList> call, Throwable t) {
                // Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b2=true;
                if(b1)
                    pDialog.dismiss();
            }
        });
        super.onResume();
    }
    @Override
    public void onPause() {
        if(pDialog.isShowing())
            pDialog.dismiss();
        popularTvShows.cancel();
        mostRatedTVShows.cancel();
        super.onPause();
    }
}
