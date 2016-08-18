package com.example.megha.movieplate.TVFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.example.megha.movieplate.WatchlistFormat.ApiClientWatchlist;
import com.example.megha.movieplate.WatchlistFormat.PostJsonInWatchList;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HIman$hu on 4/1/2016.
 */
public class TVLinearlayoutFragment extends Fragment {

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10;
    ImageView imgV1, imgV2, imgV3, imgV4, imgV5, imgV6, imgV7, imgV8, imgV9, imgV10;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
    Boolean state1, state2, state3, state4, state5, state6, state7, state8, state9, state10;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tv_linear_layout_fragment, container, false);
        Bundle b = getArguments();
        final TV tv = (TV) b.getSerializable(Constants.TV_TO_LINEAR_LAYOUT_FRAGMENT);

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

        imgV1 = (ImageView) view.findViewById(R.id.id_Img1);
        imgV2 = (ImageView) view.findViewById(R.id.id_Img2);
        imgV3 = (ImageView) view.findViewById(R.id.id_Img3);
        imgV4 = (ImageView) view.findViewById(R.id.id_Img4);
        imgV5 = (ImageView) view.findViewById(R.id.id_Img5);
        imgV6 = (ImageView) view.findViewById(R.id.id_Img6);
        imgV7 = (ImageView) view.findViewById(R.id.id_Img7);
        imgV8 = (ImageView) view.findViewById(R.id.id_Img8);
        imgV9 = (ImageView) view.findViewById(R.id.id_Img9);
        imgV10 = (ImageView) view.findViewById(R.id.id_Img10);

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
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(0).getPoster_path()).resize(400, 600).into(iv1);
            tv1.setText(tv.TVShowresults.get(0).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(1).getPoster_path()).resize(400, 600).into(iv2);
            tv2.setText(tv.TVShowresults.get(1).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(2).getPoster_path()).resize(400, 600).into(iv3);
            tv3.setText(tv.TVShowresults.get(2).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(3).getPoster_path()).resize(400, 600).into(iv4);
            tv4.setText(tv.TVShowresults.get(3).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(4).getPoster_path()).resize(400, 600).into(iv5);
            tv5.setText(tv.TVShowresults.get(4).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(5).getPoster_path()).resize(400, 600).into(iv6);
            tv6.setText(tv.TVShowresults.get(5).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(6).getPoster_path()).resize(400, 600).into(iv7);
            tv7.setText(tv.TVShowresults.get(6).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(7).getPoster_path()).resize(400, 600).into(iv8);
            tv8.setText(tv.TVShowresults.get(7).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(8).getPoster_path()).resize(400, 600).into(iv9);
            tv9.setText(tv.TVShowresults.get(8).getName());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + tv.TVShowresults.get(9).getPoster_path()).resize(400, 600).into(iv10);
            tv10.setText(tv.TVShowresults.get(9).getName());
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


        SharedPreferences sp = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        final String key = sp.getString(Constants.API_KEY, null);
        final String UserId = sp.getString(Constants.ID_SP, null);
        final String SessionId = sp.getString(Constants.SESSION_ID_SP, null);


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        state1 = sharedPref.getBoolean("state1", false);
        state2 = sharedPref.getBoolean("state2", false);
        state3 = sharedPref.getBoolean("state3", false);
        state4 = sharedPref.getBoolean("state4", false);
        state5 = sharedPref.getBoolean("state5", false);
        state6 = sharedPref.getBoolean("state6", false);
        state7 = sharedPref.getBoolean("state7", false);
        state8 = sharedPref.getBoolean("state8", false);
        state9 = sharedPref.getBoolean("state9", false);
        state10 = sharedPref.getBoolean("state10", false);


        imgV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(0).getId();
                imgV1.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV1.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state1) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state1 = !state1;
                editor.putBoolean("state1", state1);
                editor.commit();

            }
        });

        imgV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(1).getId();
                imgV2.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV2.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state2) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state2 = !state2;
                editor.putBoolean("state2", state2);
                editor.commit();
            }
        });

        imgV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(2).getId();
                imgV3.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV3.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state3) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state3 = !state3;
                editor.putBoolean("state3", state3);
                editor.commit();
            }
        });

        imgV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(3).getId();
                imgV4.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV4.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state4) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state4 = !state4;
                editor.putBoolean("state4", state4);
                editor.commit();
            }
        });

        imgV5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(4).getId();
                imgV5.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV5.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state5) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state5 = !state5;
                editor.putBoolean("state5", state5);
                editor.commit();
            }
        });

        imgV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(5).getId();
                imgV6.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV6.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide first view when second view is visible
                if (!state6) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state6 = !state6;
                editor.putBoolean("state6", state6);
                editor.commit();
            }
        });

        imgV7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(6).getId();
                imgV7.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV7.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state7) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state7 = !state7;
                editor.putBoolean("state7", state7);
                editor.commit();
            }
        });

        imgV8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(7).getId();
                imgV8.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV8.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state8) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state8 = !state8;
                editor.putBoolean("state8", state8);
                editor.commit();
            }
        });

        imgV9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(8).getId();
                imgV9.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV9.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state9) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state9 = !state9;
                editor.putBoolean("state9", state9);
                editor.commit();
            }
        });

        imgV10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tv.TVShowresults.get(9).getId();
                imgV10.setImageDrawable(getResources().getDrawable(R.drawable.transition));
                TransitionDrawable drawable = (TransitionDrawable) imgV10.getDrawable();
                drawable.setCrossFadeEnabled(true);//To hide First view when second view is visible
                if (!state10) {
                    drawable.startTransition(100);
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, true);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Added to WatchList Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    drawable.resetTransition();
                    PostJsonInWatchList postJsonInWatchList = new PostJsonInWatchList("tv", id, false);
                    Call<PostJsonInWatchList> call = ApiClientWatchlist.getInterface().createJson(UserId, key, SessionId, postJsonInWatchList);
                    call.enqueue(new Callback<PostJsonInWatchList>() {
                        @Override
                        public void onResponse(Call<PostJsonInWatchList> call, Response<PostJsonInWatchList> response) {
                            if (response.isSuccessful()) {
                                Log.i("Response Message", response.code() + response.message().toString());
                                Toast.makeText(getActivity(), "TV Show Removed from the WatchList", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostJsonInWatchList> call, Throwable t) {
                            Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                state10 = !state10;
                editor.putBoolean("state10", state10);
                editor.commit();
            }
        });
        return view;
    }

}
