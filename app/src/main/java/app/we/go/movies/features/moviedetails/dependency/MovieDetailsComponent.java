package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.ActivityScope;
import app.we.go.movies.features.moviedetails.MovieDetailsFragment;
import app.we.go.movies.features.moviedetails.tab.MovieInfoTabFragment;
import app.we.go.movies.features.moviedetails.tab.MovieReviewsTabFragment;
import app.we.go.movies.features.moviedetails.tab.VideosTabFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Subcomponent(modules = {MovieDetailsModule.class})
@ActivityScope
public interface MovieDetailsComponent {
    void inject(MovieDetailsFragment movieDetailsFragment);

    void inject(MovieReviewsTabFragment movieReviewsTabFragment);

    void inject(VideosTabFragment videosTabFragment);

    void inject(MovieInfoTabFragment movieInfoTabFragment);
}

