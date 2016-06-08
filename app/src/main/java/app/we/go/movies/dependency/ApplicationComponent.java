package app.we.go.movies.dependency;

/**
 * Created by apapad on 9/03/16.
 */

import javax.inject.Singleton;

import app.we.go.movies.features.moviedetails.MovieDetailsActivity;
import app.we.go.movies.features.moviedetails.dependency.DetailsServiceModule;
import app.we.go.movies.features.moviedetails.dependency.MovieDetailsComponent;
import app.we.go.movies.features.moviedetails.dependency.MovieDetailsModule;
import app.we.go.movies.features.moviedetails.dependency.MovieInfoComponent;
import app.we.go.movies.features.moviedetails.dependency.MovieInfoModule;
import app.we.go.movies.features.moviedetails.dependency.MovieReviewsComponent;
import app.we.go.movies.features.moviedetails.dependency.MovieReviewsModule;
import app.we.go.movies.features.moviedetails.dependency.MovieVideosComponent;
import app.we.go.movies.features.moviedetails.dependency.MovieVideosModule;
import app.we.go.movies.features.movielist.dependency.MovieListComponent;
import app.we.go.movies.features.movielist.dependency.MovieListModule;
import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class,
        ServiceModule.class,
        ApplicationAndroidModule.class,
        DatabaseModule.class})
public interface ApplicationComponent {

    MovieDetailsComponent plus(DetailsServiceModule detailsServiceModule,
                               MovieDetailsModule movieDetailsModule);
    MovieReviewsComponent plus(MovieReviewsModule module);
    MovieVideosComponent plus(MovieVideosModule module);

    MovieInfoComponent plus(DetailsServiceModule detailsServiceModule,
                            MovieInfoModule movieInfoModule);



    MovieListComponent plus(MovieListModule movieListModule);

    void inject(MovieDetailsActivity movieDetailsActivity);
}
