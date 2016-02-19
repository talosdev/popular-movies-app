package app.we.go.movies.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import app.we.go.movies.R;
import app.we.go.movies.constants.Intents;
import app.we.go.movies.ui.MovieDetailsFragment;

import hugo.weaving.DebugLog;

public class MovieDetailActivity extends Activity {

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        long movieId = getIntent().getLongExtra(Intents.EXTRA_MOVIE_ID, 0L);

        if (savedInstanceState == null) {
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieId);

            getFragmentManager().beginTransaction().add(R.id.detail_frame, fragment).commit();
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
}
