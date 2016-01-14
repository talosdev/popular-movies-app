package com.talosdev.movies.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import com.talosdev.movies.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO move to personal testing library
 * Created by apapad on 2016-01-14.
 */
public abstract class BaseDbHelperAndroidTestCase<T extends SQLiteOpenHelper> extends AndroidTestCase {

    protected SQLiteDatabase db;


    /**
     * Returns the name of the database
     * @return
     */
    public abstract String getDatabaseName();

    /**
     * Returns the {@link Class} of the database helper (implementation of
     * {@link SQLiteOpenHelper}
     * @return
     */
    public abstract Class<T> getDbHelperClass();


    /**
     * Returns a {@link Map} that defines the schema of the database.
     * The keys of the map are the table names, and the values are
     * sets of strings with the names of the columns.
     * @return
     */
    protected abstract Map<String, Set<String>> getSchemaHashSet();

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext.deleteDatabase(getDatabaseName());

        Class<?> clazz = Class.forName(getDbHelperClass().getName());
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        T dbHelper = (T) constructor.newInstance(new Object[]{mContext});
        db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        db.close();
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }


    /**
     * Test that the tables are created correctly
     * @throws Exception
     */
    public void testTablesExist() throws Exception {

        // Get cursor with all table names in the database
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: The database contains no tables at all",
                c.moveToFirst());

        Set<String> tablesSet = getTablesSet();
        // Iterate through the cursor and remove from the set as we go
        do {
            // The database contains more internal tables than the ones that
            // we want to check that exist, so we cannot assert that the
            // remove operation has removed a set element
            tablesSet.remove(c.getString(0));
        } while (c.moveToNext());

        // The set should now be empty
        assertTrue("Error: The database does not contain the required tables: " +
                        StringUtils.convertSetToMultilineString(tablesSet),
                tablesSet.isEmpty());

    }


    /**
     * Tests that all tables have the required columns.
     * @throws Exception
     */
    public void testTableColumns() throws Exception {
        
        Set<String> tables = getTablesSet();

        HashMap<String, Set<String>> schema = getSchemaHashSet();


        for(String table: tables) {
            // Query table columns from the database
            Cursor c = db.rawQuery("PRAGMA table_info(" + table + ")",
                    null);
            int columnNameIndex = c.getColumnIndex("name");

            Set<String> columns = schema.get(table);

            assertTrue("Error: Could not get column information for table " + table,
                    c.moveToFirst());


            // Iterate through the cursor and remove from the set as we go
            do {
                String columnName = c.getString(columnNameIndex);
                columns.remove(columnName);
            } while(c.moveToNext());

            // The columns set must now be empty
            assertTrue("Error: The table " + table + " does not contain the required columns: " +
                            StringUtils.convertSetToMultilineString(columns),
                    columns.isEmpty());
        }
    }

    /**
     * Utility method that returns a {@link Set} with the table names
     * from the schema map.
     * @return
     */
    private Set<String> getTablesSet() {
        return getSchemaHashSet().keySet();
    }

}
