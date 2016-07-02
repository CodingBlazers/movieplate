package com.example.megha.movieplate.MovieFormat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.Database_Connect.Image_Converter;
import com.example.megha.movieplate.Database_Connect.SQL_Helper;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Megha on 31-03-2016.
 */
public class MovieLinearLayoutFragment extends Fragment{

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_linear_layout_fragment, container, false);
        Bundle b = getArguments();
        final Movie movie = (Movie) b.getSerializable(Constants.MOVIE_TO_LINEAR_LAYOUT_FRAGMENT);
        String type=b.getString("type");
        SQL_Helper helper=new SQL_Helper(getActivity(),1);
        SQLiteDatabase db=helper.getReadableDatabase();
        String tablename="";
        if(type.equals("top_rated_movies"))
            tablename=helper.TOP_RATED_MOV_TABLE_NAME;
            if(type.equals("movie_popular"))
                tablename=helper.POPULAR_MOV_TABLE_NAME;
                if(type.equals("now"))
                    tablename=helper.NOW_PLAYING_TABLE_NAME;
                    if(type.equals("upcoming"))
                        tablename=helper.UPCOMING_MOV_TABLE_NAME;



        Cursor c=db.query(true,tablename, new String[]{helper._ID, helper.TABLE_IMAGE, helper.TABLE_OBJECT}, null, null, null, null, helper._ID, null);
        c.moveToNext();
        iv1 = (ImageView) view.findViewById(R.id.imageView1);
        iv2 = (ImageView) view.findViewById(R.id.imageView2);
        iv3 = (ImageView) view.findViewById(R.id.imageView3);
        iv4 = (ImageView) view.findViewById(R.id.imageView4);
        iv5 = (ImageView) view.findViewById(R.id.imageView5);
        iv6 = (ImageView) view.findViewById(R.id.imageView6);
        iv7 = (ImageView) view.findViewById(R.id.imageView7);
        iv8 = (ImageView) view.findViewById(R.id.imageView8);
        iv9 = (ImageView) view.findViewById(R.id.imageView9);
        iv10 = (ImageView) view.findViewById(R.id.imageView10);


        //here load from database instead
        if(b != null){

            int i=c.getColumnIndex(helper.TABLE_IMAGE);
            byte[] img_byte;
            Bitmap img;
            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv1.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv2.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv3.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv4.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv5.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv6.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv7.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv8.setImageBitmap(img);c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv9.setImageBitmap(img);
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv10.setImageBitmap(img);



//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(0).getPoster_path()).into(iv1);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(1).getPoster_path()).into(iv2);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(2).getPoster_path()).into(iv3);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(3).getPoster_path()).into(iv4);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(4).getPoster_path()).into(iv5);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(5).getPoster_path()).into(iv6);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(6).getPoster_path()).into(iv7);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(7).getPoster_path()).into(iv8);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(8).getPoster_path()).into(iv9);
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/"+movie.results.get(9).getPoster_path()).into(iv10);
        }

        final Intent i = new Intent();
        i.setClass(getActivity(), SingleMovieActivity.class);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(0));
                startActivity(i);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(1));
                startActivity(i);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(2));
                startActivity(i);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(3));
                startActivity(i);
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(4));
                startActivity(i);
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(5));
                startActivity(i);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(6));
                startActivity(i);
            }
        });
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(7));
                startActivity(i);
            }
        });
        iv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(8));
                startActivity(i);
            }
        });
        iv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(Constants.SINGLE_MOVIE_DETAILS, movie.results.get(9));
                startActivity(i);
            }
        });
        return view;
    }
}
