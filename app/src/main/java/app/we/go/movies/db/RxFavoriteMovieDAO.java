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

    Observable<DatabaseChange<FavoriteMovie>> getChangesObservable();
}
