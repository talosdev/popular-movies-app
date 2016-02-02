package com.talosdev.movies.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.talosdev.movies.R;
import com.talosdev.movies.callbacks.MovieSelectedCallback;
import com.talosdev.movies.constants.Fragments;
import com.talosdev.movies.constants.Intents;
import com.talosdev.movies.ui.MovieDetailsFragment;

import hugo.weaving.DebugLog;

public class MainActivity extends Activity implements MovieSelectedCallback {

    private boolean twoPane;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The existence of the detail frame in the activity will tell us if we are on
        // mobile or on tablet
        if (findViewById(R.id.detail_frame) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                getFragmentManager().
                        beginTransaction().
                        addToBackStack(null).
                        replace(R.id.detail_frame, new MovieDetailsFragment(), Fragments.MOVIE_DETAILS_FRAGMENT_TAG).
                        commit();
            }

        } else {
            twoPane = false;
        }
    }

    @DebugLog
    @Override
    protected void onResume() {
        super.onResume();
    }

    @DebugLog
    @Override
    protected void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @DebugLog
    @Override
    protected void onStop() {
        super.onStop();
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMovieSelected(long movieId) {

        if (twoPane) {
            // TABLET
            MovieDetailsFragment details = MovieDetailsFragment.newInstance(movieId);
            getFragmentManager().beginTransaction().replace(R.id.detail_frame, details).addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            // MOBILE
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(Intents.EXTRA_MOVIE_ID, movieId);
            startActivity(intent);
        }
    }

}
