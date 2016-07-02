package com.example.megha.movieplate;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.megha.movieplate.Database_Connect.Image_Converter;
import com.example.megha.movieplate.Database_Connect.SQL_Helper;
import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.MovieFormat.Movie;
import com.example.megha.movieplate.MovieFormat.MovieFragment;
import com.example.megha.movieplate.MovieFormat.MovieLinearLayoutFragment;
import com.example.megha.movieplate.TVFormat.ApiClientTVDb;
import com.example.megha.movieplate.TVFormat.TV;
import com.example.megha.movieplate.TVFormat.TVLinearlayoutFragment;
import com.example.megha.movieplate.TVFormat.TVShowDetails;

import java.io.IOException;
import java.sql.Blob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    //From onCreateview we get to know about what type of view we have to attach(landscape/portrait)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        SQL_Helper helper=new SQL_Helper(getActivity(),1);
        SQLiteDatabase db=helper.getReadableDatabase();
//now_playing_movies_dbms_object_of movies fetching
        Cursor c=db.query(true,helper.NOW_PLAYING_TABLE_NAME,new String[]{helper._ID,helper.TABLE_IMAGE,helper.TABLE_OBJECT},null,null,null,null,helper._ID,null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] movie_now_playing=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    Movie movie_object_now_playing= (Movie)Image_Converter.toObject(movie_now_playing);
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();

                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT,movie_object_now_playing);
                    b.putString("type","now");
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutnowplayingMovies, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
        //ends here
        //now_playing_movies_dbms_object_of movies fetching
         c=db.query(true,helper.UPCOMING_MOV_TABLE_NAME,new String[]{helper._ID,helper.TABLE_IMAGE,helper.TABLE_OBJECT},null,null,null,null,helper._ID,null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] movie_upcoming_mov=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    Movie movie_object_upcoming= (Movie)Image_Converter.toObject(movie_upcoming_mov);
                    MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();

                    b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT,movie_object_upcoming);
                    b.putString("type","upcoming");
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutupcomingmovies, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
        //ends here
        //now_playing_movies_dbms_object_of movies fetching
         c=db.query(true,helper.ON_AIR_TV_TABLE_NAME,new String[]{helper._ID,helper.TABLE_IMAGE,helper.TABLE_OBJECT},null,null,null,null,helper._ID,null);
        while(c.moveToNext())
        {
            Bundle b = new Bundle();
            if(c.getCount()==0)
            {
                byte[] tv_on_air=c.getBlob(c.getColumnIndex(helper.TABLE_OBJECT));

                try {
                    TV tv_object_on_air= (TV)Image_Converter.toObject(tv_on_air);
                    TVLinearlayoutFragment mf = new TVLinearlayoutFragment();

                    b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT,tv_object_on_air);
                    b.putString("type","on_air_movies");
                    mf.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.frameLayoutonairTvshows, mf).commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
        //ends here


//        Bundle b = getArguments();
//        String key = b.getString(Constants.MOVIE_URL_API_KEY);
//        Call<Movie> now_playing_movies = ApiClientMoviedb.getInterface().getNowPlayingMovies(key);
//        now_playing_movies.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                Movie movie = response.body();
//                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
//                Bundle b = new Bundle();
//                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
//                mf.setArguments(b);
//                getFragmentManager().beginTransaction().replace(R.id.frameLayoutnowplayingMovies, mf).commit();
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
//            }
//        });
//        Call<Movie> upcoming_movies=ApiClientMoviedb.getInterface().getUpcomingMovies(key);
//        upcoming_movies.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                Movie movie = response.body();
//                MovieLinearLayoutFragment mf = new MovieLinearLayoutFragment();
//                Bundle b = new Bundle();
//                b.putSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT, movie);
//                mf.setArguments(b);
//                getFragmentManager().beginTransaction().replace(R.id.frameLayoutupcomingmovies, mf).commit();
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
//            }
//        });
//        Call<TV> on_air_tv_shows= ApiClientTVDb.getInterface().getOnAirTVShows(key);
//        on_air_tv_shows.enqueue(new Callback<TV>() {
//            @Override
//            public void onResponse(Call<TV> call, Response<TV> response) {
//                TV tv = response.body();
//                TVLinearlayoutFragment mf = new TVLinearlayoutFragment();
//                Bundle b = new Bundle();
//                b.putSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT, tv);
//                mf.setArguments(b);
//                getFragmentManager().beginTransaction().replace(R.id.frameLayoutonairTvshows, mf).commit();
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
