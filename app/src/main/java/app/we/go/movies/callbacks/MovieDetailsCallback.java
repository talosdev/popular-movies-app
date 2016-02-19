package app.we.go.movies.callbacks;

import app.we.go.movies.remote.json.Movie;

/**
 * Callback that is called when the details of a movie are received.
 * Created by apapad on 27/01/16.
 */
public interface MovieDetailsCallback {

    void onMovieDetailsReceived(Movie movie);
}
