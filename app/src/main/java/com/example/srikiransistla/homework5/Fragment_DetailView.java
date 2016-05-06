package com.example.srikiransistla.homework5;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

public class Fragment_DetailView extends Fragment {
    private static final String ARG_MOVIE = "movie";
    private HashMap<String, ?> movie;
    ShareActionProvider mShareActionProvider;

    private int total;

    public interface OnListItemSelectedListener {
        public void onListItemSelected(HashMap<String, ?> movie);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.menu_share) == null)
            inflater.inflate(R.menu.menu_detail_fragment, menu);


        MenuItem shareItem = menu.findItem(R.id.menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");

        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movie.get("name"));
        mShareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public static Fragment_DetailView newInstance(HashMap<String, ?> movie) {
        Fragment_DetailView fragment = new Fragment_DetailView();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_DetailView() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            movie = (HashMap<String, ?>) getArguments().getSerializable(ARG_MOVIE);
        }

        if (savedInstanceState != null)
            total = savedInstanceState.getInt("Total");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detail, container, false);

        final TextView name = (TextView) rootView.findViewById(R.id.detail_name);
        final TextView year = (TextView) rootView.findViewById(R.id.detail_year);
        final TextView duration = (TextView) rootView.findViewById(R.id.detail_duration);
        final TextView director = (TextView) rootView.findViewById(R.id.detail_director_name);
        final TextView cast = (TextView) rootView.findViewById(R.id.detail_cast_names);
        final TextView description = (TextView) rootView.findViewById(R.id.detail_description);
        final ImageView poster = (ImageView) rootView.findViewById(R.id.detail_poster);
        final RatingBar rating = (RatingBar) rootView.findViewById(R.id.detail_rating);
        final TextView ratingNum = (TextView) rootView.findViewById(R.id.detail_rating_num);

        String movieName = (String) movie.get("name");
        String movieYear = (String) movie.get("year");
        String movieDuration = (String) movie.get("length");
        String movieDirector = (String) movie.get("director");
        String movieCast = (String) movie.get("stars");
        String movieDescription = (String) movie.get("description");
        Double movieRating = ((Double) movie.get("rating"));
        Double movieRatingHalf = movieRating / 2;
        Integer moviePoster = (Integer) movie.get("image");

        name.setText(movieName);
        year.setText(movieYear);
        duration.setText(movieDuration);
        director.setText(movieDirector);
        cast.setText(movieCast);
        description.setText(movieDescription);
        poster.setImageResource(moviePoster);
        float f = movieRatingHalf.floatValue();
        rating.setRating(f);
        ratingNum.setText(movieRating + "");

        return rootView;
    }

}

