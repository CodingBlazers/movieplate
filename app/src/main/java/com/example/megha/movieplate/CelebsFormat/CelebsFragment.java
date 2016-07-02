package com.example.megha.movieplate.CelebsFormat;

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
import com.example.megha.movieplate.TVFormat.TV;
import com.example.megha.movieplate.TVFormat.TVLinearlayoutFragment;

import java.io.IOException;

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
        SQL_Helper helper=new SQL_Helper(getActivity(),1);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query(true, helper.CELEB_TABLE_NAME, new String[]{helper._ID, helper.TABLE_IMAGE, helper.TABLE_OBJECT}, null, null, null, null, helper._ID, null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] celeb=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    Celebs celebs_object= (Celebs) Image_Converter.toObject(celeb);
                    CelebsLinearLayoutFragment mf = new CelebsLinearLayoutFragment();

                    b.putSerializable(Constants.CELEBS_TO_LINEAR_LAYOUT_FRAGMENT,celebs_object);
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.CelebsFramelayout, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
//        Bundle b = getArguments();
//        final String key = b.getString(Constants.CELEBS_URL_API_KEY);
//        Call<Celebs> PopularPeople = ApiClientCelebDb.getInterface().getPopularPerson(key);
//        PopularPeople.enqueue(new Callback<Celebs>() {
//            @Override
//            public void onResponse(Call<Celebs> call, Response<Celebs> response) {
//                if (response.isSuccessful()) {
//                    Celebs celebs = response.body();
//                    Bundle b = new Bundle();
//                    CelebsLinearLayoutFragment cf = new CelebsLinearLayoutFragment();
//                    b.putSerializable(Constants.CELEBS_TO_LINEAR_LAYOUT_FRAGMENT, celebs);
//                    b.putSerializable(Constants.API_KEY,key);
//                    cf.setArguments(b);
//                    getFragmentManager().beginTransaction().replace(R.id.CelebsFramelayout, cf).commit();
//                }
//                else {
//                    Toast.makeText(getActivity(), response.code() + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Celebs> call, Throwable t) {
//                Toast.makeText(getActivity(), "You Are Not Connected To Internet", Toast.LENGTH_LONG).show();
//            }
//        });
        return view;
    }
}
