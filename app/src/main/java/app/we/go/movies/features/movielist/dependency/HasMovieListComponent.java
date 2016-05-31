package app.we.go.movies.features.movielist.dependency;

/**
 * Interface that should be implemented by the class that creates and holds a copy
 * of the {@link app.we.go.movies.features.movielist.dependency.MovieListComponent} (typically the activity).
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface HasMovieListComponent {
    MovieListComponent getComponent();
}
