package com.talosdev.movies.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.talosdev.movies.contract.MoviesContract.MovieEntry;
import com.talosdev.movies.util.DatabaseTestUtils;

/**
 * Created by apapad on 2016-01-14.
 */
public class MovieCrudATC extends AbstractDatabaseATC<MovieDbHelper> {


    @Override
    public String getDatabaseName() {
        return MovieDbHelper.DATABASE_NAME;
    }

    @Override
    public Class<MovieDbHelper> getDbHelperClass() {
        return MovieDbHelper.class;
    }


    public void testInsertAndQuery() {

        ContentValues testValues = createValues();


        long rowId = db.insert(MovieEntry.TABLE_ΝΑΜΕ, null, testValues);

        // Verify we got a row back.
        assertTrue(rowId != -1);

        Cursor cursor = db.query(
                MovieEntry.TABLE_ΝΑΜΕ,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );


        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());


        DatabaseTestUtils.validateCurrentRecord("Error: The values retrieved from the db " +
                        "are not the same as the ones inserted",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from location query",
                cursor.moveToNext() );


        cursor.close();
        db.close();
    }

    private ContentValues createValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry._ID, 3123L);
        cv.put(MovieEntry.COLUMN_TITLE, "Test movie title !");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "Test movie overview. A boy suddenly realizes that....");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "2013/01/01");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, 7.6);
        cv.put(MovieEntry.COLUMN_VOTE_COUNT, 1000);

        return cv;
    }


}
