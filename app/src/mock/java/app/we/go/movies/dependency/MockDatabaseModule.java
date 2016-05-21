package app.we.go.movies.dependency;

import javax.inject.Singleton;

import app.we.go.db.InMemoryFavoriteMoviesDAO;
import app.we.go.movies.db.FavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockDatabaseModule {


    @Provides
    @Singleton
    public FavoriteMovieDAO provideFavoriteMovieDAO() {
        return new InMemoryFavoriteMoviesDAO();
    }
}
