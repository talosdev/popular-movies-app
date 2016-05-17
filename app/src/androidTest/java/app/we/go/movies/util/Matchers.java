package app.we.go.movies.util;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class Matchers {

    /**
     * Checks that
     *
     * Taken from
     * http://stackoverflow.com/a/30361345/5758378
     *
     * @param size
     * @return
     */
    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                return ((ListView) view).getChildCount() == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }
}
