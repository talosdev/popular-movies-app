package app.we.go.movies.dependency;

import javax.inject.Singleton;

import app.we.go.movies.db.RxInMemoryFavoriteMoviesDAO;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockDatabaseModule {



    @Provides
    @Singleton
    public RxFavoriteMovieDAO provideRxFavoriteMovieDAO() {
        return new RxInMemoryFavoriteMoviesDAO();
    }

}
