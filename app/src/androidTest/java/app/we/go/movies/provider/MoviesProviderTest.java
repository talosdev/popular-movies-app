package app.we.go.movies.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;
import app.we.go.movies.db.MovieDbHelper;
import app.we.go.movies.util.ContextBasedTest;
import app.we.go.movies.util.DatabaseTestUtils;
import app.we.go.movies.util.TestDataHelper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 2016-01-15.
 */
@RunWith(AndroidJUnit4.class)
public class MoviesProviderTest extends ContextBasedTest {

    public static final long MOVIE_ID = 1000l;
    public static final Uri URI_FAVORITE_WITH_ID = FavoriteMovieEntry.buildFavoriteMovieUri(MOVIE_ID);
    private static final Uri URI_FAVORITE = FavoriteMovieEntry.CONTENT_URI;
    private static Context ctx;
    private static MovieDbHelper dbHelper;
    private static SQLiteDatabase db;
    public static ContentValues testValues;

    private static final String POSTER_PATH = "/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg";


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

    @After
    public void clearTables() {
        DatabaseTestUtils.clearTables(dbHelper, FavoriteMovieEntry.TABLE_ΝΑΜΕ);
    }

    @Test
    public void testUriMatcher() {
        UriMatcher matcher = MoviesProvider.buildUriMatcher();

        assertThat(matcher.match(URI_FAVORITE_WITH_ID)).
                isEqualTo(MoviesProvider.FAVORITES_ITEM);

        assertThat(matcher.match(URI_FAVORITE)).
                isEqualTo(MoviesProvider.FAVORITES_LIST);

    }


    @Test
    public void testInsertAndCheck() {
        insertFavorite();

        // Check by getting all favorites
        Cursor c = getContext().getContentResolver().query(FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertThat(c.getCount()).isEqualTo(1);

        assertThat(c.moveToFirst());

        int idCol = c.getColumnIndex(FavoriteMovieEntry._ID);
        int movieIdCol = c.getColumnIndex(FavoriteMovieEntry.COLUMN_MOVIE_ID);
        int posterCol = c.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH);
        assertThat(c.getLong(idCol)).isGreaterThan(0l);
        assertThat(c.getLong(movieIdCol)).isEqualTo(1000l);
        assertThat(c.getString(posterCol)).isEqualTo(POSTER_PATH);


        // Check specifically
        Cursor c1 = getContext().getContentResolver().query(FavoriteMovieEntry.buildFavoriteMovieUri(MOVIE_ID),
                null,
                null,
                null,
                null);

        assertThat(c1.getCount()).isEqualTo(1);

        assertThat(c1.moveToFirst());

        assertThat(c1.getLong(idCol)).isGreaterThan(0l);
        assertThat(c1.getLong(movieIdCol)).isEqualTo(1000l);
        assertThat(c1.getString(posterCol)).isEqualTo(POSTER_PATH);

    }

    private void insertFavorite() {
        Uri returnedUri = getContext().getContentResolver().insert(URI_FAVORITE_WITH_ID, createContentValues());
        assertThat(returnedUri).isNotNull();
        assertThat(FavoriteMovieEntry.getMovieIdFromUri(returnedUri)).isEqualTo(MOVIE_ID);
    }

    @NonNull
    private ContentValues createContentValues() {
        ContentValues values = new ContentValues();
        values.put(FavoriteMovieEntry.COLUMN_POSTER_PATH, POSTER_PATH);
        return values;
    }

    @Test
    public void testInsertAndDelete() {
        insertFavorite();

        Uri insertUri2 = FavoriteMovieEntry.buildFavoriteMovieUri(2000l);
        Uri uri2 = getContext().getContentResolver().insert(
                insertUri2, createContentValues());
        assertThat(uri2).isNotNull();
        assertThat(FavoriteMovieEntry.getMovieIdFromUri(uri2)).isEqualTo(2000l);


        Cursor c = getContext().getContentResolver().query(FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertThat(c.getCount()).isEqualTo(2);


        int deleted = getContext().getContentResolver().delete(URI_FAVORITE_WITH_ID, null, null);
        assertThat(deleted).isEqualTo(1);

        Cursor c2 = getContext().getContentResolver().query(FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        assertThat(c2.getCount()).isEqualTo(1);


    }



    @Test
    public void testQueryOrder() throws Exception {
        insertFavorite();
        Uri insertUri2 = FavoriteMovieEntry.buildFavoriteMovieUri(2000l);
        Uri uri2 = getContext().getContentResolver().insert(
                insertUri2, createContentValues());
        Uri insertUri3 = FavoriteMovieEntry.buildFavoriteMovieUri(3000l);
        Uri uri3 = getContext().getContentResolver().insert(
                insertUri3, createContentValues());


        Cursor c = getContext().getContentResolver().query(URI_FAVORITE, null, null, null, FavoriteMovieEntry.COLUMN_MOVIE_ID + " DESC");
        c.moveToFirst();
        int movieIdCol = c.getColumnIndex(FavoriteMovieEntry.COLUMN_MOVIE_ID);
        assertThat(c.getLong(movieIdCol)).isEqualTo(3000l);
        c.moveToNext();
        assertThat(c.getLong(movieIdCol)).isEqualTo(2000l);
        c.moveToNext();
        assertThat(c.getLong(movieIdCol)).isEqualTo(1000l);

    }





}