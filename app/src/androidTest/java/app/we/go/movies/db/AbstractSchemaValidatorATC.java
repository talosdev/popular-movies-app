package app.we.go.movies.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * TODO move to personal testing library
 * Abstract test class that can be used to validate the creation of database schemas
 * (table names and columns). The concrete implementations only need to implement
 * a set of methods that return basic info on the database (eg database name, table names,
 * columns per table etc).
 * Created by apapad on 2016-01-14.
 */
public abstract class AbstractSchemaValidatorATC<T extends SQLiteOpenHelper>
        extends AbstractDatabaseATC<T> {

    /**
     * Method to be implemented by concrete subclasses.
     *
     * @return a {@link Map} that defines the schema of the database.
     * The keys of the map are the table names, and the values are
     * sets of strings with the names of the columns.
     */
    protected abstract Map<String, Set<String>> getSchemaHashMap();

    /**
     * Test that the tables are created correctly
     *
     * @throws Exception
     */
  //  @Test
    public void testTablesExist() throws Exception {

        // Get cursor with all table names in the database
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: The database contains no tables at all",
                c.moveToFirst());

        Set<String> expectedTablesSet = getTablesSet();

        Set<String> observedTablesSet = new HashSet<>();
        // Iterate through the cursor and remove from the set as we go
        do {
            String tableName = c.getString(0);
            observedTablesSet.add(tableName);
        } while (c.moveToNext());

        assertThat(observedTablesSet).containsAll(expectedTablesSet);

        c.close();
    }


    /**
     * Tests that all tables have the required columns.
     *
     * @throws Exception
     */
 //   @Test
    public void testTableColumns() throws Exception {

        Set<String> tables = getTablesSet();

        Map<String, Set<String>> schema = getSchemaHashMap();


        for (String table : tables) {
            // Query table columns from the database
            Cursor c = db.rawQuery("PRAGMA table_info(" + table + ")",
                    null);
            int columnNameIndex = c.getColumnIndex("name");

            Set<String> expectedColumns = schema.get(table);

            assertTrue("Error: Could not get column information for table",
                    c.moveToFirst());

            Set<String> observedColumns = new HashSet<>();

            // Iterate through the cursor and remove from the set as we go
            do {
                String columnName = c.getString(columnNameIndex);
                observedColumns.add(columnName);
            } while (c.moveToNext());


            assertThat(observedColumns).hasSameElementsAs(expectedColumns);

            c.close();
        }
    }

    /**
     * Utility method that returns a {@link Set} with the table names
     * from the schema map.
     *
     * @return the keyset of the schema hash map.
     */
    private Set<String> getTablesSet() {
        return getSchemaHashMap().keySet();
    }

}
