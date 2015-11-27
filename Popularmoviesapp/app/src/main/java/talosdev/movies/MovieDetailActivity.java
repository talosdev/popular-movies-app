package talosdev.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import talosdev.movies.constants.Intents;
import talosdev.movies.remote.FetchMovieDetailsTask;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_rel);

        String movieId = getIntent().getLongExtra(Intents.EXTRA_MOVIE_ID, 0L) + "";

        FetchMovieDetailsTask fetcher = new FetchMovieDetailsTask(this);
        fetcher.execute(movieId);

    }
}
