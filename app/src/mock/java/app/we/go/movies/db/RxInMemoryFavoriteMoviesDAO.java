package app.we.go.movies.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.we.go.movies.model.db.FavoriteMovie;
import nl.nl2312.rxcupboard.DatabaseChange;
import rx.Observable;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class RxInMemoryFavoriteMoviesDAO implements RxFavoriteMovieDAO {

    private final Map<Long, FavoriteMovie> map =
            new HashMap<>();


    @Override
    public boolean put(FavoriteMovie favoriteMovie) {
        map.put(favoriteMovie.getMovieId(), favoriteMovie);
        return true;
    }

    @Override
    public boolean delete(long movieId) {
        return map.remove(movieId) != null;
    }

    @Override
    public Observable<Boolean> check(long movieId) {
        return Observable.just(map.containsKey(movieId));
    }

    /**
     * Ignores offset and limit and returns all elements
     *
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Observable<List<FavoriteMovie>> get(int offset, int limit) {
        List<FavoriteMovie> arrayList = new ArrayList(map.values());
        return Observable.just(arrayList);
    }

    @Override
    public Observable<DatabaseChange<FavoriteMovie>> getChangesObservable() {
        return null;
    }


}
