package com.example.megha.movieplate;

import android.app.SearchManager;
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.megha.movieplate.CelebsFormat.CelebsFragment;
import com.example.megha.movieplate.MovieFormat.MovieFragment;
import com.example.megha.movieplate.SignInPackage.LogOutActivity;
import com.example.megha.movieplate.TVFormat.TvFragment;
import com.example.megha.movieplate.WatchlistFormat.WatchlistFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.SearchView.*;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Here we use toolbar(coordinate layout) in case of action bar and add a line .NoActionBar in manifests.
        //-->android.support.design.widget.CoordinatorLayout in xml file which will also support the previous android version.

        //Toolbar tells that three lines of drawer is clicked and it will opens the drawer.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        setTitle("Home");

        // We can't use getFragmentManager() because our fragment is support fragment. Also, we need support fragment because view pager is not there in fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.i("Home_Activity: ", "On back pressed");
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT");
            if (searchFragment != null && searchFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);


        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Query", query);
                Call<Search> mySearch = ApiClientOmdb.getInterface().getMySearch("", query);
                mySearch.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            Search s = response.body();
                            Bundle b = new Bundle();
                            SearchFragment sf = new SearchFragment();
                            b.putSerializable("SearchContent", s);
                            sf.setArguments(b);

                            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf, "SEARCH_FRAGMENT").addToBackStack(null).commit();
                        } else {
                            Toast.makeText(HomeActivity.this, response.code() + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "You are not connected to Internet", Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
//        /*searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
//                String api_key = sp.getString(Constants.API_KEY, null);
//                setTitle("Home");
//                HomeFragment hf = new HomeFragment();
//                Bundle b = new Bundle();
//                b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
//                hf.setArguments(b);
//                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
//                return true;
//            }
//        });*/



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
            Intent i = new Intent();
            i.setClass(HomeActivity.this, LogOutActivity.class);
            startActivity(i);
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences sp = getSharedPreferences("MoviePlate", Context.MODE_PRIVATE);
        String api_key = sp.getString(Constants.API_KEY, null);

        // launching home fragment for click on home in navigation button
        if (id == R.id.nav_home) {
            setTitle("Home");
            HomeFragment hf=new HomeFragment();
            Bundle b = new Bundle();
            b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
            hf.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
        }

        // launching movie fragment for click on maovie in navigation button
        else if (id == R.id.nav_movie) {
            setTitle("Movies");
            MovieFragment mf = new MovieFragment();
            Bundle b = new Bundle();
            b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
            mf.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mf).commit();
        }

        // launching celebs fragment for click on celebs in navigation button
        else if (id == R.id.nav_celebs) {
            setTitle("Most Popular Celebs");
            CelebsFragment celebsFragment=new CelebsFragment();
            Bundle b=new Bundle();
            b.putSerializable(Constants.CELEBS_URL_API_KEY,api_key);
            celebsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,celebsFragment).commit();

        }

        // launching TV fragment for click on TV shows in navigation button
        else if (id == R.id.nav_tv) {
            setTitle("TV");
            TvFragment tvFragment=new TvFragment();
            Bundle b=new Bundle();
            b.putSerializable(Constants.TV_URL_API_KEY,api_key);
            tvFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,tvFragment).commit();
        }

        // to do
        else if (id == R.id.nav_rating) {

        }

        // launching watchlist fragment for click on watchlist in navigation button
        else if (id == R.id.nav_watchlist) {
            setTitle("Watchlist");
            String sessionId = sp.getString(Constants.SESSION_ID_SP, null);
            String userId = sp.getString(Constants.ID_SP, null);
            WatchlistFragment watchlistFragment = new WatchlistFragment();
            Bundle b = new Bundle();
            b.putSerializable(Constants.WATCHLIST_URL_API_KEY, api_key);
            b.putSerializable(Constants.WATCHLIST_URL_SESSION_ID, sessionId);
            b.putSerializable(Constants.WATCHLIST_URL_USER_ID, userId);
            watchlistFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, watchlistFragment, "Show Watchlist Fragment").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
