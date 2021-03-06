package app.we.go.movies.remote;

import android.os.AsyncTask;
import android.util.Log;

import app.we.go.movies.callbacks.MovieDetailsCallback;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.Movie;

import java.io.IOException;

/**
 * Created by apapad on 13/11/15.
 */

public class FetchMovieDetailsTask extends AsyncTask<Long, Void, Movie> {


    private final MovieDetailsCallback callback;


    public FetchMovieDetailsTask(MovieDetailsCallback callback) {
        super();
        this.callback = callback;

    }

    @Override
    protected Movie doInBackground(Long... params) {


        try {

            MovieDetailsFetcher moviesFetcher = new MovieDetailsFetcher();
            Movie movie = moviesFetcher.fetch(params[0]);

            return movie;
        } catch (IOException e) {
            Log.e(Tags.REMOTE, "Error when contacting the server to get the movie details", e);
            return null;
        }

    }


    // TODO check if this thing that I am doing with casting the activity to activity is the best way.
    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        if (movie == null) {
            return;
        }

        callback.onMovieDetailsReceived(movie);

    }


}
