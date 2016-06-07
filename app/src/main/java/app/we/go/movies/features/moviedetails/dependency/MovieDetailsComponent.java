package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@FragmentScope
@Subcomponent(modules = {DetailsServiceModule.class, MovieDetailsModule.class})
public interface MovieDetailsComponent {
    void inject(MovieDetailsFragment movieDetailsFragment);

}

