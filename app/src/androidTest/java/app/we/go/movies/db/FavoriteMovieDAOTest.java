package app.we.go.movies.db;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import app.we.go.movies.TestData;
import app.we.go.movies.model.db.FavoriteMovie;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implementation-independent test for {@link FavoriteMovieDAO}.
 * Subclasses just need to set the {@link FavoriteMovieDAO#dao} in the {@link org.junit.BeforeClass}
 * method.
 *
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class FavoriteMovieDAOTest {
    protected static final String TEST_DB = CupboardSQLiteOpenHelper.DATABASE_NAME + ".test";
    protected static final String TABLE_NAME = FavoriteMovie.class.getSimpleName();
    private static final String SECOND_POSTER_PATH = "second/poster/path";
    private static final long SECOND_MOVIE_ID = 333L;
    protected static FavoriteMovieDAO dao;
    protected static SQLiteDatabase db;

    @AfterClass
    public static void tearDownClass() throws Exception {
        db.close();
    }

    @Before
    public void setUp() throws Exception {
        assertNumRows(0L);
    }

    @After
    public void tearDown() throws Exception {
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
    public void testGet() throws Exception {
        boolean beforeInserting = dao.get(TestData.MOVIE_ID);
        assertThat(beforeInserting).isFalse();

        putTestMovie();
        boolean afterInserting = dao.get(TestData.MOVIE_ID);
        assertThat(afterInserting).isTrue();

        boolean inexistentMovie = dao.get(TestData.MOVIE_ID_INEXISTENT);
        assertThat(inexistentMovie).isFalse();
    }

    @Test
    public void testGetAll() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        putTestMovie();

        final FavoriteMovie secondFavoriteMovie = new FavoriteMovie(SECOND_MOVIE_ID,
                SECOND_POSTER_PATH);
        dao.put(secondFavoriteMovie);

        dao.getAll(new FavoriteMovieDAO.Callback<List<FavoriteMovie>>() {
            @Override
            public void onSuccess(List<FavoriteMovie> result) {
                latch.countDown();
                assertThat(result.size()).isEqualTo(2);

                assertThat(result.get(1)).isEqualToComparingFieldByField(secondFavoriteMovie);
                assertThat(result.get(0).getPosterPath()).isEqualTo(TestData.MOVIE_POSTER_PATH);
                assertThat(result.get(0).getMovieId()).isEqualTo(TestData.MOVIE_ID);
            }

            @Override
            public void onError() {
            }
        });

        latch.await(10, TimeUnit.SECONDS);
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
