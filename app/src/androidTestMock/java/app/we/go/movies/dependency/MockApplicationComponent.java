package app.we.go.movies.dependency;

import javax.inject.Singleton;

import app.we.go.movies.features.movielist.FavoritesTest;
import dagger.Component;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        MockServiceModule.class,
        ApplicationAndroidModule.class,
        MockDatabaseModule.class})
public interface MockApplicationComponent extends ApplicationComponent {
    void inject(FavoritesTest favoritesTest);
}