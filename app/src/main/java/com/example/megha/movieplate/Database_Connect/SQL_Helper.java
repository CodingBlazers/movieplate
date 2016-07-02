package com.example.megha.movieplate.Database_Connect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sachin on 6/26/2016.
 */
public class SQL_Helper extends SQLiteOpenHelper {

    final static public  String  DATABASE_NAME="DB_FETCH_IMAGES";
    final static public  String NOW_PLAYING_TABLE_NAME="now_playing";
    final static public String UPCOMING_MOV_TABLE_NAME="upcoming_mov";
    final static public  String ON_AIR_TV_TABLE_NAME="on_air_tv";
    final static public String TOP_RATED_MOV_TABLE_NAME="top_rated_mov";
    final static public String POPULAR_MOV_TABLE_NAME="popular_mov";
    final static public String POPULAR_TV_TABLE_NAME="popular_tv";
    final static public String MOST_RATED_TV_TABLE_NAME="most_rated_tv";
    final static public String CELEB_TABLE_NAME="celeb";
    //columns
    final static public String TABLE_IMAGE="Images_blob";
    final static public String TABLE_OBJECT="mov_object";
    final static public String _ID="_id";

    public SQL_Helper(Context context, int version) {
        super(context,DATABASE_NAME,null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ NOW_PLAYING_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB," +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ UPCOMING_MOV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ ON_AIR_TV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ POPULAR_MOV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ TOP_RATED_MOV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ MOST_RATED_TV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ POPULAR_TV_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");
        db.execSQL("CREATE TABLE "+ CELEB_TABLE_NAME + "(" + _ID + " TEXT,"+TABLE_OBJECT+" BLOB,"  +TABLE_IMAGE +" BLOB);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
