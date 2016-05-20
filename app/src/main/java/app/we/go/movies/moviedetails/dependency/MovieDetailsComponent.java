package app.we.go.movies.moviedetails.dependency;

import app.we.go.movies.framework.ActivityScope;
import app.we.go.movies.moviedetails.MovieDetailsFragment;
import app.we.go.movies.moviedetails.tab.MovieInfoTabFragment;
import app.we.go.movies.moviedetails.tab.MovieReviewsTabFragment;
import app.we.go.movies.moviedetails.tab.VideosTabFragment;
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

