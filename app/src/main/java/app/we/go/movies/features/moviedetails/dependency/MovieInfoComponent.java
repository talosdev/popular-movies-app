package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.tab.MovieInfoTabFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@FragmentScope
@Subcomponent(modules = {DetailsServiceModule.class, MovieInfoModule.class})
public interface MovieInfoComponent {
    void inject(MovieInfoTabFragment movieInfoTabFragment);
}