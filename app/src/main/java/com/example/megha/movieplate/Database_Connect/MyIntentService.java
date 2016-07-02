package com.example.megha.movieplate.Database_Connect;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.example.megha.movieplate.CelebsFormat.ApiClientCelebDb;
import com.example.megha.movieplate.CelebsFormat.Celebs;
import com.example.megha.movieplate.CelebsFormat.CelebsLinearLayoutFragment;
import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.MovieFormat.ApiClientMoviedb;
import com.example.megha.movieplate.MovieFormat.Movie;
import com.example.megha.movieplate.MovieFormat.MovieLinearLayoutFragment;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.TVFormat.ApiClientTVDb;
import com.example.megha.movieplate.TVFormat.TV;
import com.example.megha.movieplate.TVFormat.TVLinearlayoutFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.megha.movieplate.Database_Connect.action.FOO";
    private static final String ACTION_BAZ = "com.example.megha.movieplate.Database_Connect.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.megha.movieplate.Database_Connect.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.megha.movieplate.Database_Connect.extra.PARAM2";
    Context context=this;

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ///HOME ACTIVITY CALLS
        final ResultReceiver reciever=intent.getParcelableExtra("RECIEVER");
        String key=intent.getStringExtra("KEY");
        Call<Movie> now_playing_movies = ApiClientMoviedb.getInterface().getNowPlayingMovies(key);
        now_playing_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final Movie movie = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0){
                                try {
                                     bytes_object=Image_Converter.toByteArray(movie);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.NOW_PLAYING_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + movie.results.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }
                else
                {
                    reciever.send(1,bundle);
                }



            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Bundle bundle=new Bundle();
                bundle.putString("msg","INTERNET NOT CONNECTED");
                reciever.send(2,bundle);

            }
        });
        Call<Movie> upcoming_movies=ApiClientMoviedb.getInterface().getUpcomingMovies(key);
        upcoming_movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final Movie movie = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0){
                                try {
                                    bytes_object=Image_Converter.toByteArray(movie);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.UPCOMING_MOV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + movie.results.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


        Call<TV> on_air_tv_shows= ApiClientTVDb.getInterface().getOnAirTVShows(key);
        on_air_tv_shows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final TV tv = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if (finalI ==0)
                                {
                                try {
                                    bytes_object=Image_Converter.toByteArray(tv);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.ON_AIR_TV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }


            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {

            }
        });
        ////MOVIE ACTUVITY CALLS
        Call<Movie> topRatedMovies = ApiClientMoviedb.getInterface().getTopRatedMovie(key);
        topRatedMovies.enqueue(new Callback<Movie>() {
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final Movie movie = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0)
                                {
                                try {
                                    bytes_object=Image_Converter.toByteArray(movie);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.UPCOMING_MOV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + movie.results.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });



        Call<Movie> popularMovies = ApiClientMoviedb.getInterface().getPopularMovie(key);
        popularMovies.enqueue(new Callback<Movie>() {
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final Movie movie = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0)
                                {try {
                                    bytes_object=Image_Converter.toByteArray(movie);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.UPCOMING_MOV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + movie.results.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        /////TV FRAGMENT CALLS
        Call<TV> popularTvShows = ApiClientTVDb.getInterface().getPopularTvShows(key);
        popularTvShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final TV tv = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0)
                                {try {
                                    bytes_object=Image_Converter.toByteArray(tv);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.ON_AIR_TV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }


            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {

            }
        });

        Call<TV> MostratedTvShows = ApiClientTVDb.getInterface().getMostratedTvShows(key);
        MostratedTvShows.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final TV tv = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0)
                                {try {
                                    bytes_object=Image_Converter.toByteArray(tv);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.ON_AIR_TV_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(i).getPoster_path()).into(target);


                    }


                    reciever.send(0,bundle);
                }


            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {

            }
        });
        //CELEB ACTIVITY CALLS
        Call<Celebs> PopularPeople = ApiClientCelebDb.getInterface().getPopularPerson(key);
        PopularPeople.enqueue(new Callback<Celebs>() {
            public void onResponse(Call<Celebs> call, Response<Celebs> response) {
                Bundle bundle=new Bundle();
                bundle.putString("msg",response.message());

                if(response.isSuccessful())
                {
                    final Celebs celebs = response.body();

                    for(int i=0;i<10;i++)
                    {

                        //take result object ,find photo for it...store both object and photo in db
                        final int finalI = i;
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Bitmap is loaded, use image here
                                byte[] bytes_image=Image_Converter.getBytes(bitmap);
                                byte[] bytes_object=null;
                                if(finalI ==0){
                                try {
                                    bytes_object=Image_Converter.toByteArray(celebs);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }}
                                SQL_Helper helper=new SQL_Helper(context,1);
                                SQLiteDatabase db=helper.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(helper.TABLE_IMAGE,bytes_image);
                                contentValues.put(helper.TABLE_OBJECT,bytes_object);
                                db.insert(helper.CELEB_TABLE_NAME,null,contentValues);


                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        };
                        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/" + celebs.CelebsList).into(target);


                    }


                    reciever.send(0,bundle);
                }


            }

            @Override
            public void onFailure(Call<Celebs> call, Throwable t) {

            }
        });}
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
