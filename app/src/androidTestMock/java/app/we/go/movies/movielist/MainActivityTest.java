package app.we.go.movies.movielist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import app.we.go.movies.R;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.json.Movie;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static app.we.go.movies.matchers.Matchers.withRecyclerView;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class, true,
                    true); // no intent arguments, so we can launch already


    @Test
    public void testSpinner() throws Exception {

        onView(withId(R.id.sort_by_spinner)).check(matches(isDisplayed()));
        // The spinner is always in sort by popularity mode when we launch the activity
        onView(withId(R.id.sort_by_spinner)).check(matches(
                hasDescendant(withText(R.string.sort_by_popularity))));

        // click the spinner
        onView(withId(R.id.sort_by_spinner)).perform(click());
        // sort by options are displayed
        onView(withText(R.string.sort_by_popularity)).check(matches(isDisplayed()));
        onView(withText(R.string.sort_by_vote)).check(matches(isDisplayed()));
        onView(withText(R.string.favorites)).check(matches(isDisplayed()));


        // select sort by vote
        onView(withText(R.string.sort_by_vote)).perform(click());
        // options disappear, only the selected one is displayed
        onView(withText(R.string.sort_by_vote)).check(matches(isDisplayed()));
        onView(withText(R.string.sort_by_popularity)).check(doesNotExist());
        onView(withText(R.string.favorites)).check(doesNotExist());


        // Selection is reflected on spinner
        onView(withId(R.id.sort_by_spinner)).check(matches(
                hasDescendant(withText(R.string.sort_by_vote))));

    }

    @Test
    public void testScroll() throws Exception {

        Movie firstMovie = DummyData.MOVIE_LIST_POPULAR_1.getMovies().get(0);

        // Check that first movie is displayed at position 0
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(0)).
                check(matches(withContentDescription(firstMovie.getTitle())));

        Thread.sleep(5000);
        // Check that only 20 movies have been loaded, the RecyclerView should not
        // contain anything at position 20 and after that
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(20)).
                check(doesNotExist());


        // Scroll towards the last items of the first batch
        onView(withId(R.id.movie_recycler_view)).
                perform(RecyclerViewActions.scrollToPosition(19));
        Thread.sleep(5000);

        Movie m = DummyData.MOVIE_LIST_POPULAR_2.getMovies().get(0);

        // Check that now we have loaded the movie at position 20
        onView(withId(R.id.movie_recycler_view)).
                perform(RecyclerViewActions.scrollToPosition(20));
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(20)).
                check(matches(withContentDescription(m.getTitle())));

    }

}