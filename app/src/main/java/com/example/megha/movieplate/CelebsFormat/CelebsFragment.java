package com.example.megha.movieplate.CelebsFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.MovieDBApiClient;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class CelebsFragment extends Fragment {

    ProgressDialog pDialog;
    boolean paused;
    Call<Celebs> popularPeople;
    SharedPreferencesUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_celebs_fragment, container, false);
        checkConnectivity();
        spUtils = new SharedPreferencesUtils(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        return view;
    }

    private void checkConnectivity() {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        pDialog.show();
        paused = false;
        final String key = spUtils.getAPIKey();
        popularPeople = MovieDBApiClient.getInterface().getPopularPerson(key);
        popularPeople.enqueue(new Callback<Celebs>() {
            @Override
            public void onResponse(Call<Celebs> call, Response<Celebs> response) {
                if (response.isSuccessful()) {
                    Celebs celebs = response.body();
                    Bundle b = new Bundle();
                    CelebsLinearLayoutFragment cf = new CelebsLinearLayoutFragment();
                    b.putSerializable(Constants.CELEBS_TO_LINEAR_LAYOUT_FRAGMENT, celebs);
                    b.putSerializable(Constants.API_KEY, key);
                    cf.setArguments(b);
                    if(!paused)
                        getFragmentManager().beginTransaction().replace(R.id.CelebsFramelayout, cf).commit();
                }
                else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Celebs> call, Throwable t) {
                pDialog.dismiss();
            }
        });
        super.onResume();
    }

    @Override
    public void onPause() {
        if(pDialog.isShowing())
            pDialog.dismiss();
        popularPeople.cancel();
        super.onPause();
    }

}
