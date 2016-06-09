package app.we.go.movies.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import app.we.go.movies.model.db.FavoriteMovie;
import nl.nl2312.rxcupboard.DatabaseChange;
import nl.nl2312.rxcupboard.RxCupboard;
import nl.nl2312.rxcupboard.RxDatabase;
import nl.qbusict.cupboard.DatabaseCompartment;
import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class RxCupboardFavoriteMovieDAO implements RxFavoriteMovieDAO {

    private static final String COLUMN_MOVIE_ID = "movieId";
    private final RxDatabase rxdb;
    private final SQLiteDatabase db;

    public RxCupboardFavoriteMovieDAO(SQLiteDatabase db) {
        rxdb = RxCupboard.with(cupboard(), db);
        this.db = db;
    }

    @Override
    public boolean put(FavoriteMovie favoriteMovie) {
        long id = rxdb.put(favoriteMovie);
        return id != -1;

    }

    @Override
    public boolean delete(long movieId) {

        FavoriteMovie favoriteMovie = cupboard().
                withDatabase(db).
                query(FavoriteMovie.class).
                withSelection("movieId = ?", movieId + "").
                query().get();

        if (favoriteMovie == null) {
            return false;
        } else {
            return rxdb.delete(FavoriteMovie.class, favoriteMovie.getId());
        }
    }

    @Override
    public Observable<FavoriteMovie> get(long movieId) {
        Observable<FavoriteMovie> observable = rxdb.
                query(FavoriteMovie.class, COLUMN_MOVIE_ID + " = ?", movieId + "");

        return observable;

    }

    @Override
    public Observable<List<FavoriteMovie>> get(int offset, int limit) {

        DatabaseCompartment.QueryBuilder<FavoriteMovie> queryBuilder =
                rxdb.buildQuery(FavoriteMovie.class).limit(limit);

        if (offset > 0) {
            queryBuilder = queryBuilder.offset(offset);
        }

        return rxdb.query(queryBuilder).toList();

    }

    @Override
    public Observable<DatabaseChange<FavoriteMovie>> getChangesObservable() {
        return rxdb.changes(FavoriteMovie.class);
    }

}
