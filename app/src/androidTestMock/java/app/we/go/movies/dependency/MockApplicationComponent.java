package app.we.go.movies.dependency;

import javax.inject.Singleton;

import app.we.go.movies.db.RxFavoriteMovieDAOTest;
import app.we.go.movies.features.movielist.FavoritesTest;
import dagger.Component;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        MockServiceModule.class,
        MockApplicationAndroidModule.class,
        DatabaseModule.class})
public interface MockApplicationComponent extends ApplicationComponent {

    // Here we can have inject methods for test, so that the tests can modify the mocked
    // injected components' behaviour.

    void inject(FavoritesTest favoritesTest);
    void inject(RxFavoriteMovieDAOTest rxCupboardFavoriteMovieDAOTest);
}