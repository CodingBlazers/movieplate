package com.example.megha.movieplate;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.example.megha.movieplate.CelebsFormat.CelebsFragment;
import com.example.megha.movieplate.MovieFormat.MovieFragment;
import com.example.megha.movieplate.SignInPackage.SignInScreen;
import com.example.megha.movieplate.TVFormat.TvFragment;
import com.example.megha.movieplate.WatchlistFormat.FragmentWatchList;
import com.example.megha.movieplate.utility.SharedPreferencesUtils;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Constants {

    TextView userNameTextView;
    String api_key;
    SharedPreferencesUtils spUtils;

    @Override
    protected void onResume() {
        String user_name = spUtils.getUsername();
        userNameTextView.setText(user_name);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spUtils = new SharedPreferencesUtils(HomeActivity.this);

        //Here we use toolbar(coordinate layout) in case of action bar and add a line .NoActionBar in manifests.
        //-->android.support.design.widget.CoordinatorLayout in xml file which will also support the previous android version.

        //Toolbar tells that three lines of drawer is clicked and it will opens the drawer.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);

        //This is To set name of the logged in person in the navigation drawer in navigation view.
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        api_key = spUtils.getAPIKey();

        View v = navigationView.getHeaderView(0);
        userNameTextView = (TextView) v.findViewById(R.id.UserNameTextView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment hf = new HomeFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constants.MOVIE_URL_API_KEY, api_key);
        hf.setArguments(b);
        setTitle("Home");

        // We can't use getFragmentManager() because our fragment is support fragment. Also, we need support fragment because view pager is not there in fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, hf).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
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
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Query", query);
                Bundle b = new Bundle();
                SearchFragment sf = new SearchFragment();
                b.putSerializable(SEARCH_TITLE, query);
                sf.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, sf).commit();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_signOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle ("Confirm");
            builder.setMessage ("Are you sure you want to logout?");
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_confirm_layout, null);
            builder.setView(v);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    spUtils.setSignedOut();
                    Intent i = new Intent(HomeActivity.this, SignInScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    ActivityCompat.finishAffinity(HomeActivity.this);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // launching home fragment for click on home in navigation button
        if (id == R.id.nav_home) {
            setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new HomeFragment()).commit();
        }

        // launching movie fragment for click on maovie in navigation button
        else if (id == R.id.nav_movie) {
            setTitle("Movies");
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new MovieFragment()).commit();
        }

        // launching celebs fragment for click on celebs in navigation button
        else if (id == R.id.nav_celebs) {
            setTitle("Most Popular Celebs");
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new CelebsFragment()).commit();

        }

        // launching TVList fragment for click on TVList shows in navigation button
        else if (id == R.id.nav_tv) {
            setTitle("TVList");
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new TvFragment()).commit();
        } /*else if (id == R.id.nav_rating) {

        }*/

        // launching watchlist fragment for click on watchlist in navigation button
        else if (id == R.id.nav_watchlist) {
            setTitle("Watchlist");
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new FragmentWatchList()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
