package app.we.go.movies.features.moviedetails.dependency;

/**
 * Interface that should be implemented by the class that creates and holds a copy
 * of the {@link MovieDetailsComponent} (typically the activity).
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */

public interface HasMovieDetailsComponent {
    MovieDetailsComponent getComponent();
}
