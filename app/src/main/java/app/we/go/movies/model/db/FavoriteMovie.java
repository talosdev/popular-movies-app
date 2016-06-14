package app.we.go.movies.model.db;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FavoriteMovie {

    @SuppressWarnings("unused") //required by cupboard
    private Long _id;

    private long movieId;

    private String posterPath;


    public FavoriteMovie(long movieId, String posterPath) {
        this.movieId = movieId;
        this.posterPath = posterPath;    }


    public FavoriteMovie() {
    }

    public long getId() {
        return _id;
    }


    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }



}
