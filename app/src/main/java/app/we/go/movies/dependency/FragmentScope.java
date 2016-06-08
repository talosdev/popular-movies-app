package app.we.go.movies.dependency;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Fragment-based scope.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
