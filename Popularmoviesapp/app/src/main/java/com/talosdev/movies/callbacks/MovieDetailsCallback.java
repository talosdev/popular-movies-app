package com.talosdev.movies.callbacks;

import com.talosdev.movies.remote.json.Movie;

/**
 * Created by apapad on 27/01/16.
 */
public interface MovieDetailsCallback {

    void onMovieDetailsReceived(Movie movie);
}
