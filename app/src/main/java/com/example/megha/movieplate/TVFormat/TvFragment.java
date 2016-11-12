package com.example.megha.movieplate.TVFormat;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 3/31/2016.
 */
public class TvFragment extends Fragment {

    Call<TV> popularTvShows, mostRatedTVShows;
    boolean b1, b2, paused;
    ProgressDialog pDialog;
    Bundle b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv_fragment, container, false);
        b = getArguments();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");

        paused = b1 = b2 = false;

        return view;
    }

    @Override
    public void onResume() {
        pDialog.show();
        paused = false;
        String key = b.getString(Constants.TV_URL_API_KEY);

        popularTvShows = ApiClientTVDb.getInterface().getPopularTvShows(key);
        popularTvShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                if (response.isSuccessful()) {
                    TV tv = response.body();
                    Log.i("TV Object", tv.toString());
                    Bundle b = new Bundle();
                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
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
            public void onFailure(Call<TV> call, Throwable t) {
                // Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }
        });

        mostRatedTVShows = ApiClientTVDb.getInterface().getMostratedTvShows(key);
        mostRatedTVShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                if (response.isSuccessful()) {
                    TV tv = response.body();
                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
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
            public void onFailure(Call<TV> call, Throwable t) {
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
