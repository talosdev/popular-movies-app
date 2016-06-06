package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsFragment;
import app.we.go.movies.features.moviedetails.tab.MovieInfoTabFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Subcomponent(modules = {MovieDetailsModule.class})
@FragmentScope
public interface MovieDetailsComponent {
    void inject(MovieDetailsFragment movieDetailsFragment);

    void inject(MovieInfoTabFragment movieInfoTabFragment);
}

