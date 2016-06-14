package app.we.go.movies.features.movielist;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.application.TestMoviesApplication;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.dependency.MockApplicationComponent;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.remote.DummyData;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static app.we.go.movies.espresso.matchers.Matchers.withDrawable;
import static app.we.go.movies.espresso.matchers.Matchers.withRecyclerView;

/**
 * Tests that the favorites list screen is correctly updated when a movie is un-starred.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FavoritesTest {


    @Inject
    RxFavoriteMovieDAO dao;

    @Rule
    public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class, true,
                    true); // no intent arguments, so we can launch already


    @Before
    public void setUp() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        TestMoviesApplication app
                = (TestMoviesApplication) instrumentation.getTargetContext().getApplicationContext();
        MockApplicationComponent component = (MockApplicationComponent) app.getComponent();
        component.inject(this);


        List<FavoriteMovie> iterator = dao.get(0, 100).toBlocking().first();
        for (FavoriteMovie fm: iterator) {
            dao.delete(fm.getMovieId());
        }


        //Init the DAO with 2 favorite movies
        FavoriteMovie fm1 = new FavoriteMovie(DummyData.MOVIE_ID_1, DummyData.MOVIE_POSTER_PATH_1);
        FavoriteMovie fm2 = new FavoriteMovie(DummyData.MOVIE_ID_2, DummyData.MOVIE_POSTER_PATH_2);

        dao.put(fm1);
        dao.put(fm2);

    }


    @After
    public void tearDown() throws Exception {
        dao.delete(DummyData.MOVIE_ID_1);
        dao.delete(DummyData.MOVIE_ID_2);
        testRule.getActivity().finish();
    }


    /**
     * Test that when we click on a favorite movie and then go back to the favorites screen,
     * the list of favorites is correctly updated.
     *
     * @throws Exception
     */
    @Test
    public void testUnfavorite() throws Exception {
        // click the spinner
        onView(withId(R.id.sort_by_spinner)).perform(click());
        // select favorites
        onView(withText(R.string.favorites)).perform(click());


        // Check that there are two movies
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(0)).
                check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(1)).
                check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(2)).
                check(doesNotExist());

        // click on the first favorite movie
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(0)).
                perform(click());

        // the details page opens

        // click on the favorite icon
        onView(withId(R.id.menu_favorite)).perform(click());

        // check that the icon has changed
        onView(withId(R.id.menu_favorite)).
                check(matches(withDrawable(R.drawable.ic_favorite_border_blue_24dp)));

        // go back
        Espresso.pressBack();


        // Check that there is only one movie
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(0)).
                check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.movie_recycler_view).atPosition(1)).
                check(doesNotExist());


    }
}
