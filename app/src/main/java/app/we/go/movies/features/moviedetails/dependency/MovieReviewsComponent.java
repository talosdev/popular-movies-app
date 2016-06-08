package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.tab.MovieReviewsTabFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@FragmentScope
@Subcomponent(modules = {MovieReviewsModule.class})
public interface MovieReviewsComponent {


    void inject(MovieReviewsTabFragment movieReviewsTabFragment);
}