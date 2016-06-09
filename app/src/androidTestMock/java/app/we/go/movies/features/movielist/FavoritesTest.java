package app.we.go.movies.features.movielist;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;

import javax.inject.Inject;

import app.we.go.movies.application.TestMoviesApplication;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.dependency.MockApplicationComponent;

/**
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

    }
}
