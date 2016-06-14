package app.we.go.movies.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Process;

import java.util.List;

import app.we.go.movies.model.db.FavoriteMovie;
import nl.qbusict.cupboard.DatabaseCompartment;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * All methods, except {@link CupboardFavoriteMovieDAO#getAll(Callback, int, int)}
 * in this implementation are blocking methods that execute in the calling thread.
 * The exception method is a non-blocking call, that expects a callback to notify the caller,
 * though it still executes in the same thread as the caller.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class CupboardFavoriteMovieDAO implements FavoriteMovieDAO {

    private static final String COLUMN_MOVIE_ID = "movieId";
    private final SQLiteDatabase db;

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
    public void getAll(final Callback<List<FavoriteMovie>> callback, final int offset, final int limit) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                DatabaseCompartment.QueryBuilder<FavoriteMovie> queryBuilder = cupboard().
                        withDatabase(db).
                        query(FavoriteMovie.class).
                        limit(limit);

                if (offset > 0) {
                    queryBuilder = queryBuilder.offset(offset);
                }

                List<FavoriteMovie> favoriteMovieList = queryBuilder.
                        // TODO
                        // orderBy().
                                list();
                callback.onSuccess(favoriteMovieList);

            }
        };
        // Simple handler that will execute the runnable in the same thread
        // By using a Handler we also go around the problem with the notifyDataSetChanged
        // method being called while the RecyclerView is laying out itself
        Handler h = new Handler();
        h.postDelayed(runnable, 10);
    }

}
