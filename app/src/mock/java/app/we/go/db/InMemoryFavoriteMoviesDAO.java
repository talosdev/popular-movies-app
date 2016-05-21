package app.we.go.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.model.FavoriteMovie;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class InMemoryFavoriteMoviesDAO implements FavoriteMovieDAO {

    private Map<Long, FavoriteMovie> map =
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
    public boolean get(long movieId) {
        return map.containsKey(movieId);
    }

    @Override
    public void getAll(Callback<List<FavoriteMovie>> callback) {
        callback.onSuccess(new ArrayList<>(map.values()));
    }
}
