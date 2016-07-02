package com.example.megha.movieplate.TVFormat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.Database_Connect.Image_Converter;
import com.example.megha.movieplate.Database_Connect.SQL_Helper;
import com.example.megha.movieplate.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 3/31/2016.
 */
public class TvFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv_fragment, container, false);
        SQL_Helper helper=new SQL_Helper(getActivity(),1);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query(true, helper.POPULAR_TV_TABLE_NAME, new String[]{helper._ID, helper.TABLE_IMAGE, helper.TABLE_OBJECT}, null, null, null, null, helper._ID, null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] tv_popular=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    TV tv_object_popular= (TV) Image_Converter.toObject(tv_popular);
                    TVLinearlayoutFragment mf = new TVLinearlayoutFragment();

                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT,tv_object_popular);
                    b.putString("type","tv_popular");
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.id_PopularTvShows, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
        c=db.query(true,helper.MOST_RATED_TV_TABLE_NAME,new String[]{helper._ID,helper.TABLE_IMAGE,helper.TABLE_OBJECT},null,null,null,null,helper._ID,null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] tv_most_rated=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    TV tv_object_most_rated= (TV)Image_Converter.toObject(tv_most_rated);
                    TVLinearlayoutFragment mf = new TVLinearlayoutFragment();

                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT,tv_object_most_rated);
                    b.putString("type","tv_most_rated");
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.id_MostRatedTvShows, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
//        Bundle b = getArguments();
//        String key = b.getString(Constants.TV_URL_API_KEY);
//        Call<TV> popularTvShows = ApiClientTVDb.getInterface().getPopularTvShows(key);
//        popularTvShows.enqueue(new Callback<TV>() {
//            @Override
//            public void onResponse(Call<TV> call, Response<TV> response) {
//                if (response.isSuccessful()) {
//                    TV tv = response.body();
//                    Bundle b = new Bundle();
//                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
//                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
//                    tvf.setArguments(b);
//                    getFragmentManager().beginTransaction().replace(R.id.id_PopularTvShows, tvf).commit();
//                } else {
//                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TV> call, Throwable t) {
//                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Call<TV> MostratedTvShows = ApiClientTVDb.getInterface().getMostratedTvShows(key);
//        MostratedTvShows.enqueue(new Callback<TV>() {
//            @Override
//            public void onResponse(Call<TV> call, Response<TV> response) {
//                if (response.isSuccessful()) {
//                    TV tv = response.body();
//                    TVLinearlayoutFragment tvf = new TVLinearlayoutFragment();
//                    Bundle b = new Bundle();
//                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
//                    tvf.setArguments(b);
//                    getFragmentManager().beginTransaction().replace(R.id.id_MostRatedTvShows, tvf).commit();
//                } else {
//                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TV> call, Throwable t) {
//                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
//            }
//        });

        return view;
    }
}
