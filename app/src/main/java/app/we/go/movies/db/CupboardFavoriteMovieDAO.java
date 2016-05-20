package app.we.go.movies.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import app.we.go.movies.model.FavoriteMovie;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class CupboardFavoriteMovieDAO implements FavoriteMovieDAO {

    private static final String COLUMN_MOVIE_ID = "movieId";
    private SQLiteDatabase db;

    public CupboardFavoriteMovieDAO(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public boolean put(FavoriteMovie favoriteMovie) {
        long id = cupboard().
                withDatabase(db).
                put(favoriteMovie);
        return id != -1;

    }

    @Override
    public boolean delete(long movieId) {

        int count = cupboard().
                withDatabase(db).
                delete(FavoriteMovie.class, COLUMN_MOVIE_ID + " = ?", movieId + "");
        return count > 0;
    }

    @Override
    public boolean get(long movieId) {
        FavoriteMovie favoriteMovie = cupboard().
                withDatabase(db).
                query(FavoriteMovie.class).
                withSelection(COLUMN_MOVIE_ID + " = ?", movieId + "").
                get();

        return favoriteMovie != null;

    }

    @Override
    public void getAll(final Callback<List<FavoriteMovie>> callback) {
        (new Runnable() {
            @Override
            public void run() {
                List<FavoriteMovie> favoriteMovieList = cupboard().
                        withDatabase(db).
                        query(FavoriteMovie.class).
                        // orderBy().
                        list();
                callback.onSuccess(favoriteMovieList);

            }
        }).run();
    }
}
