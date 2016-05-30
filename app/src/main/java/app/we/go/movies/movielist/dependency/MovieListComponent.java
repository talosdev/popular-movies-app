package app.we.go.movies.movielist.dependency;

import app.we.go.movies.framework.ActivityScope;
import app.we.go.movies.movielist.MovieListFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Subcomponent(modules = {MovieListModule.class})
@ActivityScope
public interface MovieListComponent {
    void inject(MovieListFragment fragment);


}

