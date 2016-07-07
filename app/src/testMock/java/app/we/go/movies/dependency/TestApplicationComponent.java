package app.we.go.movies.dependency;

import javax.inject.Singleton;

import app.we.go.movies.features.moviedetails.MovieDetailsPresenterFavoritesTest;
import app.we.go.movies.features.moviedetails.MovieDetailsPresenterTest;
import app.we.go.movies.features.moviedetails.MovieInfoPresenterTest;
import app.we.go.movies.features.moviedetails.MovieReviewsPresenterTest;
import app.we.go.movies.features.moviedetails.VideosPresenterTest;
import app.we.go.movies.features.movielist.MovieListPresenterTest;
import dagger.Component;

/**
 * {@link ApplicationComponent} that can be used to inject dependencies to JUnit tests.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        SchedulersModule.class,
        ServiceModule.class,
        ApplicationAndroidModule.class,
        DatabaseModule.class})
public interface TestApplicationComponent extends ApplicationComponent {

    // Here we can have inject methods for test, so that the tests can modify the mocked
    // injected components' behaviour.

    void inject(VideosPresenterTest videosPresenterTest);

    void inject(MovieDetailsPresenterFavoritesTest movieDetailsPresenterFavoritesTest);

    void inject(MovieInfoPresenterTest movieInfoPresenterTest);

    void inject(MovieReviewsPresenterTest movieReviewsPresenterTest);

    void inject(MovieDetailsPresenterTest movieDetailsPresenterTest);

    void inject(MovieListPresenterTest movieListPresenterTest);
}