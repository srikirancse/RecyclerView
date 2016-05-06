package com.example.srikiransistla.homework5;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import java.util.HashMap;

/**
 * Created by Srikiran Sistla on 2/25/2016.
 */
public class Activity_task2 extends AppCompatActivity implements Fragment_List.OnListItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {
    private Fragment mContent;
    private Fragment mContent1;
    Toolbar mToolBar;
    android.support.v7.app.ActionBar mActionBar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    public void onListItemSelected(int position, HashMap<String, ?> movie) {

        //Load another fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycler_container, Fragment_DetailView.newInstance(movie))
                .addToBackStack(null)
                .commit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_container);

        mToolBar=(Toolbar) findViewById(R.id.rv_toolbar);
        setSupportActionBar(mToolBar);
        mActionBar=getSupportActionBar();

        //mActionBar.setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //mActionBar.setLogo(R.drawable.me);

        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, mToolBar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "mContent") != null) {
                mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            } else {
                mContent = Fragment_RecyclerView.newInstance();
            }
        } else {
            mContent = Fragment_RecyclerView.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycler_container, mContent)
                .commit();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                Toast.makeText(getApplicationContext(), "Clicked search", Toast.LENGTH_LONG).show();
                return false;
        }
        mContent1 = Fragment_RecyclerView.newInstance(); //passing id to fragment and creating new fragment

        getSupportFragmentManager().beginTransaction() //loading fragment
                .replace(R.id.container, mContent1)
                .commit();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mContent.isAdded())
            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent;

        switch (id){
            case R.id.drawer_menu1:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_menu2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recycler_container, MainActivity.MySimpleFragment.newInstance(id))
                        .commit();
                break;
            case R.id.drawer_menu3:
                intent = new Intent(this, Activity_task2.class);
                startActivity(intent);
                break;
            case R.id.drawer_menu4:
                intent = new Intent(this, Activity_task3.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
