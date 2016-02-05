package com.talosdev.movies.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.talosdev.movies.contract.MoviesContract.MovieEntry;
import com.talosdev.movies.db.MovieDbHelper;
import com.talosdev.movies.util.ContextBasedTest;
import com.talosdev.movies.util.DatabaseTestUtils;
import com.talosdev.movies.util.TestDataHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 2016-01-15.
 */
@RunWith(AndroidJUnit4.class)
public class MoviesProviderTest extends ContextBasedTest {

    public static final long MOVIE_ID = 12345L;
    public static final Uri URI_MOVIE_DETAILS = MovieEntry.buildDetailsUri(MOVIE_ID);
    private static Context ctx ;
    private static MovieDbHelper dbHelper;
    private static SQLiteDatabase db;
    public static ContentValues testValues;


    @BeforeClass
    public static void setup() {
        ctx = getContext();
        dbHelper = new MovieDbHelper(getContext());
        db = dbHelper.getWritableDatabase();

        testValues = TestDataHelper.createMovieValues();
    }

    @AfterClass
    public static void closeDb() {
        db.close();
    }

    @Before
    public void addRecordToDatabase() {
        DatabaseTestUtils.insertValues(
                db,
                MovieEntry.TABLE_ΝΑΜΕ,
                testValues);

    }

    @After
    public  void clearTables() {
        DatabaseTestUtils.clearTables(dbHelper, MovieEntry.TABLE_ΝΑΜΕ);
    }

    @Test
    public void testUriMatcher() {
        UriMatcher matcher = MoviesProvider.buildUriMatcher();

        assertThat(matcher.match(URI_MOVIE_DETAILS)).
                isEqualTo(MoviesProvider.MOVIE_DETAILS);
    }


    @Test
    public void testBasicQuery() {

        // Test the basic content provider query
        Cursor cursor = getContext().getContentResolver().query(
                MovieEntry.buildDetailsUri(TestDataHelper.TEST_MOVIE_ID),
                null,
                null,
                null,
                null
        );

        testQuery(cursor);
    }

    @Test
    public void testQueryBasedOnId() {

        // Test the basic content provider query
        Cursor cursor = getContext().getContentResolver().query(
                MovieEntry.buildDetailsUri(TestDataHelper.TEST_MOVIE_ID),
                null,
                MovieEntry._ID + "= ?",
                new String[]{TestDataHelper.TEST_MOVIE_ID + ""},
                null
        );

        testQuery(cursor);
    }


    @Test
    public void testQueryBasedOnTitle() {

        // Test the basic content provider query
        Cursor cursor = getContext().getContentResolver().query(
                MovieEntry.buildDetailsUri(TestDataHelper.TEST_MOVIE_ID),
                null,
                MovieEntry.COLUMN_TITLE + " = ?",
                new String[]{TestDataHelper.TEST_MOVIE_TITLE},
                null
        );

        testQuery(cursor);
    }


    private void testQuery(Cursor cursor) {



        cursor.moveToFirst();

        DatabaseTestUtils.validateCurrentRecord(
                "The movie that the provider returned doesn't match the one inserted to the database",
                cursor,
                testValues);
    }


}