package app.we.go.movies.db;

import android.content.ContentValues;
import android.database.Cursor;

import app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;
import app.we.go.movies.util.DatabaseTestUtils;
import app.we.go.movies.util.TestDataHelper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 2016-01-14.
 */
public class FavoriteMovieCrudATC extends AbstractDatabaseATC<MovieDbHelper> {


    @Override
    public String getDatabaseName() {
        return MovieDbHelper.DATABASE_NAME;
    }

    @Override
    public Class<MovieDbHelper> getDbHelperClass() {
        return MovieDbHelper.class;
    }


    @Test
    public void testInsertAndQuery() {

        ContentValues testValues = TestDataHelper.createMovieValues();

        long rowId = db.insert(FavoriteMovieEntry.TABLE_ΝΑΜΕ, null, testValues);

        // Verify we got a row back.
        assertThat(rowId).isNotEqualTo(-1);

        Cursor cursor = db.query(
                FavoriteMovieEntry.TABLE_ΝΑΜΕ,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );


        assertThat(cursor.moveToFirst()).
                as("Error: No Records returned from location query").
                isTrue();

        DatabaseTestUtils.validateCurrentRecord("Error: The values retrieved from the db " +
                        "are not the same as the ones inserted",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertThat(cursor.moveToNext()).
                as("Error: More than one record returned from location query").
                isFalse();


        cursor.close();
        db.close();
    }


}
