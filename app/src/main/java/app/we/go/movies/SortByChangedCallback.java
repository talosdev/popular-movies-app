package app.we.go.movies;

import app.we.go.movies.data.SortByCriterion;

/**
 * Callback interface used to notify the activity that is should update its interface to
 * reflect a (forced) change in the {@link SortByCriterion}.
 * Example: when hitting the back button.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface SortByChangedCallback {
     void sortByChanged(SortByCriterion sortBy);
}
