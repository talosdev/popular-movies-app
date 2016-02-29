package app.we.go.movies.listener;

import app.we.go.movies.remote.json.Movie;

/**
 * Callback that is called when the details of a movie are received.
 * Created by apapad on 27/01/16.
 */
public interface MovieInfoListener {

    void onMovieInfoReceived(Movie movie);
}
