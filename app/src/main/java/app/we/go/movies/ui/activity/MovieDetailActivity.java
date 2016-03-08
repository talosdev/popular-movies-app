package app.we.go.movies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import app.we.go.movies.R;
import app.we.go.movies.ui.MovieDetailsFragment;
import hugo.weaving.DebugLog;

public class MovieDetailActivity extends FragmentActivity {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private static final String EXTRA_POSTER_PATH = "EXTRA_POSTER_PATH";

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, 0L);
        String posterPath = getIntent().getStringExtra(EXTRA_POSTER_PATH);

        if (savedInstanceState == null) {
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieId, posterPath);

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame, fragment).commit();
        }
    }


    public static Intent newIntent(Context context, long movieId, String posterPath) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        intent.putExtra(EXTRA_POSTER_PATH, posterPath);
        return intent;
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


//    public MovieInfoListener getMovieInfoListener() {
//    }
}
