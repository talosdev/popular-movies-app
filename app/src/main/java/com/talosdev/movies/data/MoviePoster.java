package com.talosdev.movies.data;

import java.io.Serializable;

/**
 * Tuple that groups the movieId with the poster url.
 * Created by apapad on 26/11/15.
 */
public class MoviePoster implements Serializable {
    private long movieId;
    private String posterUrl;

    public MoviePoster(long movieId, String posterUrl) {
        this.movieId = movieId;
        this.posterUrl = posterUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
