package com.example.megha.movieplate.CelebsFormat;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by HIman$hu on 4/1/2016.
 */
public class CelebsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_celebs_fragment, container, false);
        Bundle b = getArguments();
        final String key = b.getString(Constants.CELEBS_URL_API_KEY);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        Call<Celebs> PopularPeople = ApiClientCelebDb.getInterface().getPopularPerson(key);
        PopularPeople.enqueue(new Callback<Celebs>() {
            @Override
            public void onResponse(Call<Celebs> call, Response<Celebs> response) {
                if (response.isSuccessful()) {
                    Celebs celebs = response.body();
                    Bundle b = new Bundle();
                    CelebsLinearLayoutFragment cf = new CelebsLinearLayoutFragment();
                    b.putSerializable(Constants.CELEBS_TO_LINEAR_LAYOUT_FRAGMENT, celebs);
                    b.putSerializable(Constants.API_KEY,key);
                    cf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.CelebsFramelayout, cf).commit();
                }
                else {
                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Celebs> call, Throwable t) {
                Toast.makeText(getActivity(), "You Are Not Connected To Internet", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });
        return view;
    }
}
