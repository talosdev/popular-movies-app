package app.we.go.movies.features.moviedetails;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import app.we.go.movies.remote.DummyData;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class BaseMovieDetailsActivityTest {
    @Rule
    public final ActivityTestRule<MovieDetailsActivity> testRule =
            new ActivityTestRule<>(MovieDetailsActivity.class, true,
                    false);


    @Before
    public void launchActivity() {
        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent intent = MovieDetailsActivity.newIntent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                DummyData.MOVIE_ID, DummyData.MOVIE_POSTER_PATH);
        testRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {

    }
}
