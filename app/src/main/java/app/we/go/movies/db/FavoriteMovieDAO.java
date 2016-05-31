package app.we.go.movies.db;

import java.util.List;

import app.we.go.movies.model.db.FavoriteMovie;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface FavoriteMovieDAO {

    boolean put(FavoriteMovie favoriteMovie);

    boolean delete(long movieId);

    boolean get(long movieId);

    void getAll(Callback<List<FavoriteMovie>> callback);


    interface Callback<T> {
        void onSuccess(T result);

        void onError();

    }
}
