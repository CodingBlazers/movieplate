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

    boolean b1, b2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv_fragment, container, false);
        Bundle b = getArguments();
        String key = b.getString(Constants.TV_URL_API_KEY);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        b1=false; b2=false;
        Call<TV> popularTvShows = ApiClientTVDb.getInterface().getPopularTvShows(key);
        popularTvShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                if (response.isSuccessful()) {
                    TV tv = response.body();
                    Log.i("TV Object",tv.toString());
                    Bundle b = new Bundle();
                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
                    tvf.setArguments(b);
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
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b1=true;
                if(b2)
                    pDialog.dismiss();
            }
        });

        Call<TV> MostratedTvShows = ApiClientTVDb.getInterface().getMostratedTvShows(key);
        MostratedTvShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                if (response.isSuccessful()) {
                    TV tv = response.body();
                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
                    tvf.setArguments(b);
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
                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
                b2=true;
                if(b1)
                    pDialog.dismiss();
            }
        });

        return view;
    }
}
