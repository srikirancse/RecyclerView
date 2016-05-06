package com.example.srikiransistla.homework5;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class Fragment_RecyclerView extends Fragment {

    private static final String ARG_MOVIE_DATA = "section_number";
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
    MovieData movieData = new MovieData();
    private List<Map<String, ?>> movieDataList;
    LinearLayoutManager mLayoutManager;


    public static Fragment_RecyclerView newInstance() {

        Fragment_RecyclerView fragment = new Fragment_RecyclerView();
        Bundle args = new Bundle();
        //args.putSerializable(ARG_MOVIE_DATA, (Serializable) movieData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.menu_search) == null)
            inflater.inflate(R.menu.menu_recycler, menu);

        Log.d("Message",(String) getActivity().getTitle());

        if (getActivity().getTitle().equals("Task 3") ) {
            Log.d("Message",(String) getActivity().getTitle());
            Drawable drawable = menu.findItem(R.id.menu_search).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }
        }

        SearchView search=(SearchView) menu.findItem(R.id.menu_search).getActionView();
        Log.d("Message","OnMenuOptions");
        if (search!=null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                @Override
                public boolean onQueryTextSubmit(String query) {

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    int position=movieData.getPosition(query);
                    Log.d("Position",position+"");
                    if (position>=0)
                        mRecyclerView.scrollToPosition(position);
                    else
                        Toast.makeText(getContext(),"Such Movie is not listed!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        final Fragment_List.OnListItemSelectedListener mListener;
        try {
            mListener = (Fragment_List.OnListItemSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("The hosting activity of the fragment" +
                    "forgot to implement onFragmentInteractionListener");
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                mListener.onListItemSelected(position, movie);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));

            }

            @Override
            public void onOverflowMenuClick(View v, final int position) {
                PopupMenu popup=new PopupMenu(getActivity(),v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_card_duplicate:
                                movieData.moviesList.add(position + 1, (HashMap) ((HashMap) movieData.getItem(position)).clone());
                                mRecyclerViewAdapter.notifyItemInserted(position + 1);
                                return true;
                            case R.id.menu_card_delete:
                                movieData.moviesList.remove(position);
                                mRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                MenuInflater inflater=popup.getMenuInflater();
                inflater.inflate(R.menu.menu_contextual_popup,popup.getMenu());
                popup.show();
            }
        });

        //defaultAnimation();
        adapterAnimation();
        itemAnimation();







        return rootView;

    }

    //Animation
    private void defaultAnimation() {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(500);
        animator.setRemoveDuration(100);

        mRecyclerView.setItemAnimator(animator);
    }

    private void itemAnimation() {
        ScaleInAnimator animator = new ScaleInAnimator();
        //SlideInLeftAnimator animator=new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());

        animator.setAddDuration(300);
        animator.setRemoveDuration(300);

        mRecyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation() {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mRecyclerViewAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        //SlideInRightAnimationAdapter radapter=new SlideInRightAnimationAdapter(scaleAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
    }


    public Fragment_RecyclerView() {

    }

    public class ActionBarCallBack implements ActionMode.Callback {
        int position;

        public ActionBarCallBack(int position) {
            this.position = position;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contextual_popup, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.menu_card_delete:
                    movieData.moviesList.remove(position);
                    mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.menu_card_duplicate:
                    movieData.moviesList.add(position + 1, (HashMap) ((HashMap) movieData.getItem(position)).clone());
                    mRecyclerViewAdapter.notifyItemInserted(position + 1);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
