package app.we.go.movies.model.local;

import java.io.Serializable;

/**
 * Tuple that groups the movieId with the poster url.
 * Created by apapad on 26/11/15.
 */
public class MoviePoster implements Serializable {
    private final long movieId;
    private final String posterPath;

    public MoviePoster(long movieId, String posterPath) {
        this.movieId = movieId;
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public long getMovieId() {
        return movieId;
    }

}
