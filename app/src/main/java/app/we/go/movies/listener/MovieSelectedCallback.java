package app.we.go.movies.listener;

import app.we.go.movies.remote.json.Movie;

/**
 * Callback used for fragment-enclosing activity interaction
 * when user selects a movie in the list.
 * Created by apapad on 28/01/16.
 */
public interface MovieSelectedCallback {
    void onMovieSelected(Movie movie);
}
