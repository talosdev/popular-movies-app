package app.we.go.movies.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import app.we.go.movies.db.MovieDbHelper;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * TODO move to personal test lib
 * Created by apapad on 2016-01-14.
 */
public class DatabaseTestUtils  {

    /**
     * Make sure the cursor's posistion is set (eg if the cursor is expected to
     * only have one record, call {@link Cursor#moveToFirst()})
     * @param error
     * @param valueCursor
     * @param expectedValues
     */
    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static long insertValues(SQLiteDatabase db, String tableName, ContentValues values) {

        long rowId = db.insert(tableName, null, values);

        assertThat(rowId).
                as("Inserting values to the database").
                isGreaterThan(-1);

        return rowId;
    }

    public static void clearTables(MovieDbHelper dbHelper,  String... tables) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String table: tables) {
            db.delete(table,
                    null,
                    null);
        }
    }
}
