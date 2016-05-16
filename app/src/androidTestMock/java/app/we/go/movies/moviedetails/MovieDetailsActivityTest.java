package app.we.go.movies.moviedetails;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.we.go.movies.R;
import app.we.go.movies.TestData;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@RunWith(AndroidJUnit4.class)
public class MovieDetailsActivityTest {

    @Rule
    public ActivityTestRule<MovieDetailsActivity> testRule =
            new ActivityTestRule<>(MovieDetailsActivity.class, true,
                    false);


    @Before
    public void intentWithStubbedNoteId() {


        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent intent = MovieDetailsActivity.newIntent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                TestData.MOVIE_ID, TestData.MOVIE_POSTER_PATH);
        testRule.launchActivity(intent);

//        registerIdlingResource();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDetailsAreDisplayed() throws Exception {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e ) {
//
//        }


        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.synopsis_title)).check(matches(isDisplayed()));
        onView(withId(R.id.release_date_title)).check(matches(isDisplayed()));
        onView(withId(R.id.score_title)).check(matches(isDisplayed()));

    }
}