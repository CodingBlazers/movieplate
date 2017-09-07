package com.example.megha.movieplate.CelebsFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.BuildConfig;
import com.example.megha.movieplate.utility.NoInternetActivity;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.utility.ConnectionDetector;
import com.example.megha.movieplate.utility.API.MovieDBApiClient;

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
    private RecyclerView recyclerView;
    private CelebrityListAdapter celebsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_celebs_fragment, container, false);
        checkConnectivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.celeb_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        return view;
    }

    @Override
    public void onResume() {
        pDialog.show();
        paused = false;
        popularPeople = MovieDBApiClient.getInterface().getPopularPerson(BuildConfig.MOVIE_DB_API_KEY);
        popularPeople.enqueue(new Callback<Celebs>() {
            @Override
            public void onResponse(Call<Celebs> call, Response<Celebs> response) {
                if (response.isSuccessful()) {
                    Celebs celebs = response.body();
                    celebsAdapter = new CelebrityListAdapter(getContext(), celebs.getCelebsList());
                    celebsAdapter.setOnItemClickListener(new CelebrityListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, CelebrityResults celebDetails) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), CelebrityDetailsActivity.class);
                            intent.putExtra("Profile_ID", celebDetails.getId());
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(celebsAdapter);
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

    private void checkConnectivity() {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
