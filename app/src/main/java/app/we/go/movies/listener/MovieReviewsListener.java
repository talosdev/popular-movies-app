package app.we.go.movies.listener;

import app.we.go.movies.remote.json.Movie;

/**
 * Callback that is called when the trailer list for a movie is received.
 * Created by apapad on 29/02/16.
 */
public interface MovieReviewsListener {

    void onMovieReviewsReceived(Movie movie);
}
