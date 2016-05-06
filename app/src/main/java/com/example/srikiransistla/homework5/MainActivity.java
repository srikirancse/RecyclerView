package com.example.srikiransistla.homework5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements Fragment_Interface_Main.OnListItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener{

    private Fragment mContent1;
    private Fragment mContent;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar mToolBar;

    //ActionBar actionBar = getSupportActionBar();



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        mContent1 = MySimpleFragment.newInstance(id); //passing id to fragment and creating new fragment

        getSupportFragmentManager().beginTransaction() //loading fragment
                .replace(R.id.container, mContent1)
                .commit();

        switch (id) {
            case R.id.mainmenu_act2:
                intent = new Intent(this, Activity_task2.class);
                startActivity(intent);
                break;
        }

        switch (id) {
            case R.id.mainmenu_act3:
                intent = new Intent(this, Activity_task3.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(int ref) {
        //loads fragments into the this activity on request of other fragments
        mContent = MySimpleFragment.newInstance(ref);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mContent)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simplefragment);

        mToolBar=(Toolbar) findViewById(R.id.hp_toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitleTextColor(Color.WHITE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, mToolBar , R.string.openDrawer, R.string.closeDrawer){

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


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "mContent") != null)
                mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            else
                mContent = MySimpleFragment.newInstance(R.id.mainmenu_home);

        } else {
            mContent = MySimpleFragment.newInstance(R.id.mainmenu_home);

        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mContent)
                .commit();
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
                        .replace(R.id.container, MySimpleFragment.newInstance(id))
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


    public static class MySimpleFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int counter;

        public static MySimpleFragment newInstance(int sectionNumber) {
            MySimpleFragment fragment = new MySimpleFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public MySimpleFragment() {

        }


        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState != null)
                counter = savedInstanceState.getInt("Counter");
            else
                counter = 0;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;
            int option = getArguments().getInt(ARG_SECTION_NUMBER);
            Intent intent;

            switch (option) {
                case R.id.mainmenu_home:
                    rootView = inflater.inflate(R.layout.activity_coverpage, container, false);
                    final Button aboutme = (Button) rootView.findViewById(R.id.cpbutton1);
                    final Button task2 = (Button) rootView.findViewById(R.id.cpbutton2);
                    final Button task3 = (Button) rootView.findViewById(R.id.cpbutton3);

                    final Fragment_Interface_Main.OnListItemSelectedListener mListener;
                    try {
                        mListener = (Fragment_Interface_Main.OnListItemSelectedListener) getContext();
                    } catch (ClassCastException e) {
                        throw new ClassCastException("The hosting activity of the fragment" +
                                "forgot to implement onFragmentInteractionListener");
                    }

                    aboutme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onListItemSelected(R.id.mainmenu_aboutme);
                        }
                    });

                    task2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Activity_task2.class);
                            startActivity(intent);
                        }
                    });

                    task3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Activity_task3.class);
                            startActivity(intent);
                        }
                    });

                    break;
                case R.id.mainmenu_aboutme:
                    rootView = inflater.inflate(R.layout.activity_aboutme, container, false);
                    break;

                case R.id.mainmenu_act2:
                    intent = new Intent(getActivity(), Activity_task2.class); //help taken to put getActivity()
                    startActivity(intent);
                    break;

                case R.id.mainmenu_act3:
                    intent = new Intent(getActivity(), Activity_task3.class); //help taken to put getActivity()
                    startActivity(intent);
                    break;
                case R.id.drawer_menu2:
                    rootView = inflater.inflate(R.layout.activity_aboutme, container, false);
                    break;


            }

            return rootView;


        }


    }


}

