package app.we.go.movies.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.common.BaseActivity;
import app.we.go.movies.moviedetails.dependency.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.dependency.MovieDetailsComponent;
import app.we.go.movies.moviedetails.dependency.MovieDetailsModule;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MovieDetailsActivity extends BaseActivity implements HasMovieDetailsComponent {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private static final String EXTRA_POSTER_PATH = "EXTRA_POSTER_PATH";



    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    IdlingResource idlingResource;

    private MovieDetailsComponent movieDetailsComponent;


    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);
        MovieApplication.get(this).getComponent().inject(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, 0L);
        String posterPath = getIntent().getStringExtra(EXTRA_POSTER_PATH);

        if (savedInstanceState == null) {
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieId, posterPath);

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame, fragment).commit();
        }

        movieDetailsComponent = MovieApplication.get(this).getComponent().
                plus(new MovieDetailsModule(this, movieId));
    }


    /**
     * Factory for the Intent that can be used to start this activity.
     *
     * @param context    The calling context
     * @param movieId    The movieId
     * @param posterPath The posterPath (we will need it so that we can store it in the
     *                   database if the movie is favorited by the user)
     * @return
     */
    public static Intent newIntent(Context context, long movieId, String posterPath) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
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


    @Override
    public MovieDetailsComponent getComponent() {
        return movieDetailsComponent;
    }

    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        return idlingResource;
    }
}
