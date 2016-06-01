package app.we.go.movies.remote;

import android.support.annotation.Nullable;

import javax.inject.Singleton;

import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.db.InMemoryFavoriteMoviesDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Fake {@link URLBuilder} subclass that can be used for hermetic tests.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeURLBuilder extends URLBuilder {

    public FakeURLBuilder() {
        this.getClass().getClassLoader().getResource("movie-list.json");
    }

    /**
     * Instead of returning a remote URL, we return a local file URL.
     * @param poster
     *  Ignored by this implementation
     * @param posterWidth
     *  Ignored by this implementation
     * @return
     */
    @Nullable
    @Override
    public String buildPosterUrl(String poster, int posterWidth) {
        return "file:///android_asset/poster.jpg";
    }

    /**
     * Created by Aristides Papadopoulos (github:talosdev).
     */
    @Module
    public static class MockDatabaseModule {


        @Provides
        @Singleton
        public FavoriteMovieDAO provideFavoriteMovieDAO() {
            return new InMemoryFavoriteMoviesDAO();
        }
    }
}
