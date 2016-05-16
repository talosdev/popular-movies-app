package app.we.go.movies.dependency;

/**
 * Created by apapad on 9/03/16.
 */

import javax.inject.Singleton;

import app.we.go.movies.moviedetails.dependency.MovieDetailsComponent;
import app.we.go.movies.moviedetails.dependency.MovieDetailsModule;
import app.we.go.movies.movielist.MovieListFragment;
import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class, ApplicationAndroidModule.class})
public interface ApplicationComponent {
    void inject(MovieListFragment f);


    MovieDetailsComponent plus(MovieDetailsModule module);

}
