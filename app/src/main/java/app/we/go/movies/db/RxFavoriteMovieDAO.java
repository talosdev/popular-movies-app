package app.we.go.movies.db;

import java.util.List;

import app.we.go.movies.model.db.FavoriteMovie;
import nl.nl2312.rxcupboard.DatabaseChange;
import rx.Observable;

/**
 * Rx-enabled DAO for accessing the Favorites database table.
 *
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface RxFavoriteMovieDAO {

    /**
     * Mark a movie as favorite.
     * @param favoriteMovie
     * @return
     */
    boolean put(FavoriteMovie favoriteMovie);

    /**
     * Delete from the favorites/
     * @param movieId
     * @return
     */
    boolean delete(long movieId);

    /**
     * Checks whether a movie is marked as favorite.
     * @param movieId
     * @return
     */
    Observable<Boolean> check(long movieId);


    /**
     * Returns all favorite movies. Supports pagination.
     * @param offset
     * @param limit
     * @return
     */
    Observable<List<FavoriteMovie>> get(int offset, int limit);

    /**
     * This Observable allows Observers to be notified and take appropriate actions
     * when anything is modified in the database (ie update cached values).
     *
     * @return
     */
    Observable<DatabaseChange<FavoriteMovie>> getChangesObservable();
}
