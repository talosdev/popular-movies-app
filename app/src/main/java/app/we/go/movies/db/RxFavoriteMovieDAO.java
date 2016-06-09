package app.we.go.movies.db;

import java.util.List;

import app.we.go.movies.model.db.FavoriteMovie;
import nl.nl2312.rxcupboard.DatabaseChange;
import rx.Observable;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface RxFavoriteMovieDAO {
    boolean put(FavoriteMovie favoriteMovie);

    boolean delete(long movieId);

    Observable<FavoriteMovie> get(long movieId);

    Observable<List<FavoriteMovie>> get(int offset, int limit);

    /**
     * This Observable allows Observers to be notified and take appropriate actions
     * when anything is modified in the database (ie update cached values).
     *
     * @return
     */
    Observable<DatabaseChange<FavoriteMovie>> getChangesObservable();
}
