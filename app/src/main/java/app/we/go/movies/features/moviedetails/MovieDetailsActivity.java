package app.we.go.movies.features.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import app.we.go.framework.mvp.BaseActivity;
import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.features.moviedetails.dependency.DetailsServiceModule;
import app.we.go.movies.features.moviedetails.dependency.HasDetailsServiceModule;
import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MovieDetailsActivity extends BaseActivity implements HasDetailsServiceModule {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private static final String EXTRA_POSTER_PATH = "EXTRA_POSTER_PATH";



    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private DetailsServiceModule detailsServiceModule;


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

        detailsServiceModule = new DetailsServiceModule(this, movieId);
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
    public DetailsServiceModule getModule() {
        return detailsServiceModule;
    }

}
