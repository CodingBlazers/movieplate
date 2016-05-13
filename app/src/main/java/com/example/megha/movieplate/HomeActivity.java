package com.example.megha.movieplate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.CelebsFormat.CelebsFragment;
import com.example.megha.movieplate.MovieFormat.MovieFragment;
import com.example.megha.movieplate.TVFormat.TvFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Here we use toolbar(coordinate layout)incase of action bar and add a line .NoActionBar in manifests.
        //-->android.support.design.widget.CoordinatorLayout in xml file which will also support the previous android version.
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //This button will remain fixed in every page of our application.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar snackbar=Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null);
//                View sbView=snackbar.getView();
//                sbView.setBackgroundColor(Color.BLACK);
//                TextView textView=(TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(Color.GREEN);
//                snackbar.show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HomeFragment hf=new HomeFragment();
        Bundle b = new Bundle();
        SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
        String api_key = sp.getString(Constants.API_KEY, null);
        b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
        hf.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBox);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Query",query);
                Call<Search> mySearch = ApiClientOmdb.getInterface().getMySearch("",query);
                mySearch.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            Search s = response.body();
                            Bundle b = new Bundle();
                            SearchFragment sf = new SearchFragment();
                            b.putSerializable("searchMovie", s);
                            sf.setArguments(b);
                            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
                        }
                        else {
                            Toast.makeText(HomeActivity.this, response.code() + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "You are not connected to Internet" , Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        } else if (id == R.id.action_about) {
            Intent i = new Intent();
            i.setClass(HomeActivity.this, AboutUsActivity.class);
            startActivity(i);
        } else if (id == R.id.action_contactUs) {
            Intent i = new Intent();
            i.setClass(HomeActivity.this, ContactUsActivity.class);
            startActivity(i);
        } else if (id == R.id.action_signOut) {

        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.API_KEY, "4b21312cf568464ee6b05097ebc6f824");
        editor.commit();
        if (id == R.id.nav_home) {
            setTitle("Home");
            HomeFragment hf=new HomeFragment();
            Bundle b = new Bundle();
            String api_key = sp.getString(Constants.API_KEY, null);
            b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
            hf.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
        } else if (id == R.id.nav_movie) {
            setTitle("Movies");
            MovieFragment mf = new MovieFragment();
            Bundle b = new Bundle();
            String api_key = sp.getString(Constants.API_KEY, null);
            b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
            mf.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mf).commit();
        } else if (id == R.id.nav_celebs) {
            setTitle("Most Popular Celebs");
            String api_key=sp.getString(Constants.API_KEY,null);
            CelebsFragment celebsFragment=new CelebsFragment();
            Bundle b=new Bundle();
            b.putSerializable(Constants.CELEBS_URL_API_KEY,api_key);
            celebsFragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,celebsFragment).commit();

        } else if (id == R.id.nav_tv) {
            setTitle("TV");
            TvFragment tvFragment=new TvFragment();
            Bundle b=new Bundle();
            String api_key=sp.getString(Constants.API_KEY,null);
            b.putSerializable(Constants.TV_URL_API_KEY,api_key);
            tvFragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,tvFragment).commit();
        } else if (id == R.id.nav_rating) {

        } else if (id == R.id.nav_watchlist) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
