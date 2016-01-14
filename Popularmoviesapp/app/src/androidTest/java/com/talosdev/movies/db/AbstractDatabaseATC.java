package com.talosdev.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertTrue;

/**
 * TODO move to personal testing library
 * Abstract database that can be used as the base of database-based tests.
 * Offers implementations of {@link #setUp()} and {@link #tearDown()} that
 * open and close the database.
 * Created by apapad on 2016-01-14.
 */
public abstract class AbstractDatabaseATC<T extends SQLiteOpenHelper> {
    protected SQLiteDatabase db;
    protected Context ctx;


    /**
     * Method to be implemented by concrete subclasses.
     * @return the database name
     */
    public abstract String getDatabaseName();

    /**
     * Method to be implemented by concrete subclasses.
     * @return the {@link Class} of the database helper (implementation of
     * {@link SQLiteOpenHelper}
     */
    public abstract Class<T> getDbHelperClass();




    @Before
    public void setUp() throws Exception {
        ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ctx.deleteDatabase(getDatabaseName());

        Class<?> clazz = Class.forName(getDbHelperClass().getName());
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        SQLiteOpenHelper dbHelper = (SQLiteOpenHelper) constructor.newInstance(ctx);
        db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @After
    public void tearDown() throws Exception {
        db.close();
        ctx.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }
}
