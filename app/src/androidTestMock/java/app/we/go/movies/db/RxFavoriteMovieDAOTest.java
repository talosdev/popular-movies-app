package app.we.go.movies.db;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import app.we.go.movies.TestData;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.dependency.AndroidTestApplicationComponent;
import app.we.go.movies.model.db.FavoriteMovie;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test basic crud operations using the {@link RxCupboardFavoriteMovieDAO}.
 *
 * Note that this is not an espresso test, so it won't idle-sync automatically.
 * We explicitly have to call {@link TestSubscriber#awaitTerminalEvent()}.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class RxFavoriteMovieDAOTest {
    protected static final String TABLE_NAME = FavoriteMovie.class.getSimpleName();
    private static final String SECOND_POSTER_PATH = "second/poster/path";
    private static final long SECOND_MOVIE_ID = 333L;

    @Inject
    SQLiteDatabase db;


    @Inject
    RxFavoriteMovieDAO dao;


    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ((AndroidTestApplicationComponent)
                MovieApplication.get(context).getComponent()).
                inject(this);

        cleanUpDatabase();
    }

    @After
    public void tearDown() throws Exception {
        cleanUpDatabase();
    }

    private void cleanUpDatabase() {
        db.execSQL("delete from " + TABLE_NAME + ";");
        assertNumRows(0L);
    }


    @Test
    public void testPut() throws Exception {
        putTestMovie();

    }

    @Test
    public void testDelete() throws Exception {
        putTestMovie();

        boolean delete = dao.delete(TestData.MOVIE_ID);
        assertThat(delete).isTrue();

        assertNumRows(0);
    }

    @Test
    public void testCheck() throws Exception {
        TestSubscriber<Boolean> testObs = new TestSubscriber<>();
        dao.check(TestData.MOVIE_ID).subscribe(testObs);

        assertCheckResult(testObs, false);

        putTestMovie();
        TestSubscriber<Boolean> testObs2 = new TestSubscriber<>();
        dao.check(TestData.MOVIE_ID).subscribe(testObs2);

        assertCheckResult(testObs2, true);

        TestSubscriber<Boolean> testObs3 = new TestSubscriber<>();
        dao.check(TestData.MOVIE_ID_INEXISTENT).subscribe(testObs3);
        assertCheckResult(testObs3, false);
    }

    private void assertCheckResult(TestSubscriber<Boolean> testObs, boolean expected) {
        testObs.awaitTerminalEvent();
        List<Boolean> onNextEvents = testObs.getOnNextEvents();

        assertThat(onNextEvents).hasSize(1);
        assertThat(onNextEvents.get(0)).isEqualTo(expected);
    }

    @Test
    public void testGetAll() throws Exception {

        putTestMovie();

        final FavoriteMovie secondFavoriteMovie = new FavoriteMovie(SECOND_MOVIE_ID,
                SECOND_POSTER_PATH);
        dao.put(secondFavoriteMovie);


        TestSubscriber<List<FavoriteMovie>> testSub =
                new TestSubscriber<>();
        dao.get(0, 10).subscribe(testSub);

        testSub.awaitTerminalEvent();
        testSub.assertNoErrors();
        List<List<FavoriteMovie>> onNextEvents = testSub.getOnNextEvents();

        assertThat(onNextEvents).hasSize(1);
        List<FavoriteMovie> favoriteMovies = onNextEvents.get(0);
        assertThat(favoriteMovies.size()).isEqualTo(2);

        assertThat(favoriteMovies.get(1)).isEqualToComparingFieldByField(secondFavoriteMovie);
        assertThat(favoriteMovies.get(0).getPosterPath()).isEqualTo(TestData.MOVIE_POSTER_PATH);
        assertThat(favoriteMovies.get(0).getMovieId()).isEqualTo(TestData.MOVIE_ID);


    }

    protected void putTestMovie() {
        FavoriteMovie favoriteMovie = new FavoriteMovie(TestData.MOVIE_ID,
                TestData.MOVIE_POSTER_PATH);
        dao.put(favoriteMovie);
        assertNumRows(1);
    }


    protected void assertNumRows(long expected) {
        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        assertThat(numRows).isEqualTo(expected);
    }
}
