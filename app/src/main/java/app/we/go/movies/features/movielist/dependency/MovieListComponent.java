package app.we.go.movies.features.movielist.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.movielist.MovieListFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Subcomponent(modules = {MovieListModule.class})
@FragmentScope
public interface MovieListComponent {
    void inject(MovieListFragment fragment);


}

