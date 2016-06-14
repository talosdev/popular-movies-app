package app.we.go.movies.features.moviedetails;

import android.test.UiThreadTest;

import org.hamcrest.Matchers;
import org.junit.Test;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.Review;
import app.we.go.movies.model.remote.Video;
import app.we.go.movies.remote.DummyData;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static app.we.go.movies.espresso.matchers.Matchers.withDrawable;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsActivityTest extends BaseMovieDetailsActivityTest {

    @Test
    public void testDetailsAreDisplayed() throws Exception {

        // Check that all info is shown
        onView(withId(R.id.movieTitle)).check(matches(withText(DummyData.MOVIE_TITLE)));

        onView(withId(R.id.synopsis_title)).check(matches(isDisplayed()));
        onView(withId(R.id.synopsis)).check(matches(withText(DummyData.MOVIE_OVERVIEW)));

        onView(withId(R.id.release_date_title)).check(matches(isDisplayed()));
        onView(withId(R.id.release_date)).check(matches(withText(DummyData.MOVIE_RELEASE_DATE_STR)));

        onView(withId(R.id.vote_average_title)).check(matches(isDisplayed()));
        onView(withId(R.id.vote_average)).check(matches(withText("" + DummyData.MOVIE_VOTE_AVG)));
        onView(withId(R.id.vote_count)).check(matches(withText(containsString("" + DummyData.MOVIE_VOTES))));

        // Swipe to reviews tab
        onView(withId(R.id.details_pager)).perform(swipeLeft());

        // Check that all reviews are shown, with the correct data
        for (int i = 0; i < DummyData.REVIEWS_NUM; i++) {

            onData(is(instanceOf(Review.class))).
                    inAdapterView(withId(R.id.reviews_list)).
                    atPosition(i).
                    onChildView(withId(R.id.reviewContent)).
                    check(matches(isDisplayed())).
                    check(matches(withText(DummyData.REVIEW_CONTENTS[i])));

            onData(is(instanceOf(Review.class))).
                    inAdapterView(withId(R.id.reviews_list)).
                    atPosition(i).
                    onChildView(withId(R.id.reviewAuthor)).
                    check(matches(isDisplayed())).
                    check(matches(withText(DummyData.REVIEW_AUTHORS[i])));
        }

        // Swipe to movies tab
        onView(withId(R.id.details_pager)).perform(swipeLeft());

        // Check that all movies are shown, with the correct data
        for (int i = 0; i < DummyData.VIDEOS_NUM; i++) {

            onData(is(instanceOf(Video.class))).
                    inAdapterView(withId(R.id.videos_list)).
                    atPosition(i).
                    onChildView(withId(R.id.videoName)).
                    check(matches(isDisplayed())).
                    check(matches(withText(DummyData.VIDEO_NAMES[i])));

            onData(is(instanceOf(Video.class))).
                    inAdapterView(withId(R.id.videos_list)).
                    atPosition(i).
                    onChildView(withId(R.id.videoDetails)).
                    check(matches(isDisplayed())).
                    check(matches(withText(Matchers.startsWith(DummyData.VIDEO_TYPES[i]))));

        }
    }


    @Test
    @UiThreadTest
    public void testFavoriteButton() throws Exception {

        // Favorite button displays the not-favorite icon
        onView(withId(R.id.menu_favorite)).
                check(matches(withDrawable(R.drawable.ic_favorite_border_blue_24dp)));

        // Click the favorite button
        onView(withId(R.id.menu_favorite)).perform(click());

        // Now the button displays the favorite icon
        onView(withId(R.id.menu_favorite)).
                check(matches(withDrawable(R.drawable.ic_favorite_blue_24dp)));

        // Click again to disable
        onView(withId(R.id.menu_favorite)).perform(click());

        // Not-favorite icon is shown
        onView(withId(R.id.menu_favorite)).
                check(matches(withDrawable(R.drawable.ic_favorite_border_blue_24dp)));

    }
}