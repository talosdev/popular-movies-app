package app.we.go.movies.movielist;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import app.we.go.movies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class, true,
                    true); // no intent arguments, so we can launch already


    @Test
    public void testRecyclerViewShowsElements() throws Exception {

        onView(withId(R.id.sort_by_spinner)).check(matches(isDisplayed()));

    }

}