package com.example.megha.movieplate.TVFormat;

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
import android.widget.TextView;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.Database_Connect.Image_Converter;
import com.example.megha.movieplate.Database_Connect.SQL_Helper;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class TVLinearlayoutFragment extends Fragment {

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tv_linear_layout_fragment, container, false);
        Bundle b = getArguments();
        final TV tv = (TV) b.getSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT);
        String type=b.getString("type");
        SQL_Helper helper=new SQL_Helper(getActivity(),1);
        SQLiteDatabase db=helper.getReadableDatabase();
        String tablename="";
        if(type.equals("tv_most_rated"))
            tablename=helper.MOST_RATED_TV_TABLE_NAME;
        if(type.equals("tv_popular"))
            tablename=helper.POPULAR_TV_TABLE_NAME;
        if(type.equals("on_air_movies"))
            tablename=helper.ON_AIR_TV_TABLE_NAME;
       



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
        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = (TextView) view.findViewById(R.id.textView2);
        tv3 = (TextView) view.findViewById(R.id.textView3);
        tv4 = (TextView) view.findViewById(R.id.textView4);
        tv5 = (TextView) view.findViewById(R.id.textView5);
        tv6 = (TextView) view.findViewById(R.id.textView6);
        tv7 = (TextView) view.findViewById(R.id.textView7);
        tv8 = (TextView) view.findViewById(R.id.textView8);
        tv9 = (TextView) view.findViewById(R.id.textView9);
        tv10 = (TextView) view.findViewById(R.id.textView10);

        if (b != null) {
            int i=c.getColumnIndex(helper.TABLE_IMAGE);
            byte[] img_byte;
            Bitmap img;
            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv1.setImageBitmap(img);
            tv1.setText(tv.TVShowresults.get(0).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv2.setImageBitmap(img);
            tv2.setText(tv.TVShowresults.get(1).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv3.setImageBitmap(img);
            tv3.setText(tv.TVShowresults.get(2).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv4.setImageBitmap(img);
            tv4.setText(tv.TVShowresults.get(3).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv5.setImageBitmap(img);
            tv5.setText(tv.TVShowresults.get(4).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv6.setImageBitmap(img);
            tv6.setText(tv.TVShowresults.get(5).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv7.setImageBitmap(img);
            tv7.setText(tv.TVShowresults.get(6).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv8.setImageBitmap(img);
            tv8.setText(tv.TVShowresults.get(7).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            iv9.setImageBitmap(img);
            tv9.setText(tv.TVShowresults.get(8).getName());
            c.moveToNext();

            img_byte=c.getBlob(i);
            img= Image_Converter.getImage(img_byte);
            tv10.setText(tv.TVShowresults.get(9).getName());
            iv10.setImageBitmap(img);

//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(0).getPoster_path()).into(iv1);
//            tv1.setText(tv.TVShowresults.get(0).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(1).getPoster_path()).into(iv2);
//            tv2.setText(tv.TVShowresults.get(1).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(2).getPoster_path()).into(iv3);
//            tv3.setText(tv.TVShowresults.get(2).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(3).getPoster_path()).into(iv4);
//            tv4.setText(tv.TVShowresults.get(3).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(4).getPoster_path()).into(iv5);
//            tv5.setText(tv.TVShowresults.get(4).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(5).getPoster_path()).into(iv6);
//            tv6.setText(tv.TVShowresults.get(5).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(6).getPoster_path()).into(iv7);
//            tv7.setText(tv.TVShowresults.get(6).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(7).getPoster_path()).into(iv8);
//            tv8.setText(tv.TVShowresults.get(7).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(8).getPoster_path()).into(iv9);
//            tv9.setText(tv.TVShowresults.get(8).getName());
//            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(9).getPoster_path()).into(iv10);
//            tv10.setText(tv.TVShowresults.get(9).getName());
        }

        final Intent intent = new Intent();
        intent.setClass(getActivity(), TVShowDetails.class);

        //Results class must implements Serializable otherwise class cast exception occurs.
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(0));
                startActivity(intent);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(1));
                startActivity(intent);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(2));
                startActivity(intent);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(3));
                startActivity(intent);
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(4));
                startActivity(intent);
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(5));
                startActivity(intent);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(6));
                startActivity(intent);
            }
        });
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(7));
                startActivity(intent);
            }
        });
        iv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(8));
                startActivity(intent);
            }
        });
        iv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.SINGLE_TV_SHOW_DETAILS, tv.TVShowresults.get(9));
                startActivity(intent);
            }
        });

        return view;
    }

}
