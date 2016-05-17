package app.we.go.movies.moviedetails;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import app.we.go.movies.R;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.util.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
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
                DummyData.MOVIE_ID, DummyData.MOVIE_POSTER_PATH);
        testRule.launchActivity(intent);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDetailsAreDisplayed() throws Exception {

        onView(withId(R.id.movieTitle)).check(matches(withText(DummyData.MOVIE_TITLE)));

        onView(withId(R.id.synopsis_title)).check(matches(isDisplayed()));
        onView(withId(R.id.synopsis)).check(matches(withText(DummyData.MOVIE_OVERVIEW)));


        onView(withId(R.id.release_date_title)).check(matches(isDisplayed()));
        onView(withId(R.id.release_date)).check(matches(withText(DummyData.MOVIE_RELEASE_DATE_STR)));


        onView(withId(R.id.vote_average_title)).check(matches(isDisplayed()));
        onView(withId(R.id.vote_average)).check(matches(withText("" + DummyData.MOVIE_VOTE_AVG)));
        onView(withId(R.id.vote_count)).check(matches(withText(containsString("" + DummyData.MOVIE_VOTES))));

        onView(withId(R.id.details_pager)).perform(swipeLeft());

        onView(withId(R.id.reviews_list)).check(matches(Matchers.withListSize(DummyData.REVIEWS.getReviews().size())));
        onView(withId(R.id.videos_list)).check(matches(Matchers.withListSize(DummyData.VIDEOS.getVideos().size())));


//
//        onData(instanceOf(Review.class))
//                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
//                .atPosition(3)
//                .check(matches(isDisplayed()));

    }
}