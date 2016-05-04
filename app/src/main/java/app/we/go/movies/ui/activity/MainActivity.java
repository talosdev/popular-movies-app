package app.we.go.movies.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import app.we.go.movies.R;
import app.we.go.movies.constants.Fragments;
import app.we.go.movies.listener.MovieSelectedCallback;
import app.we.go.movies.ui.MovieDetailsFragment;
import hugo.weaving.DebugLog;

public class MainActivity extends ToolbarActivity implements MovieSelectedCallback {

    private boolean twoPane;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initToolbar(false);

        // The existence of the detail frame in the activity will tell us if we are on
        // mobile or on tablet
        if (findViewById(R.id.detail_frame) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().
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
    public void onMovieSelected(long movieId, String posterPath) {

        if (twoPane) {
            // TABLET
            MovieDetailsFragment details = MovieDetailsFragment.newInstance(movieId, posterPath);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame, details).addToBackStack(null)
                  //  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            // MOBILE
            Intent intent = MovieDetailActivity.newIntent(this, movieId, posterPath);
            startActivity(intent);
        }
    }

}
