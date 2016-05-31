package app.we.go.movies.dependency;

/**
 * Created by apapad on 9/03/16.
 */

import javax.inject.Singleton;

import app.we.go.movies.features.moviedetails.MovieDetailsActivity;
import app.we.go.movies.features.moviedetails.dependency.MovieDetailsComponent;
import app.we.go.movies.features.moviedetails.dependency.MovieDetailsModule;
import app.we.go.movies.features.movielist.dependency.MovieListComponent;
import app.we.go.movies.features.movielist.dependency.MovieListModule;
import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class,
        ServiceModule.class,
        ApplicationAndroidModule.class,
        DatabaseModule.class})
public interface ApplicationComponent {

    MovieDetailsComponent plus(MovieDetailsModule module);

    MovieListComponent plus(MovieListModule movieListModule);



    void inject(MovieDetailsActivity movieDetailsActivity);
}
