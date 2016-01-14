package com.talosdev.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import java.lang.reflect.Constructor;

/**
 * TODO move to personal testing library
 * Abstract database that can be used as the base of database-based tests.
 * Offers implementations of {@link #setUp()} and {@link #tearDown()} that
 * open and close the database.
 * Created by apapad on 2016-01-14.
 */
public abstract class AbstractDatabaseATC<T extends SQLiteOpenHelper> extends AndroidTestCase {
    protected SQLiteDatabase db;

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



    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext.deleteDatabase(getDatabaseName());

        Class<?> clazz = Class.forName(getDbHelperClass().getName());
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        SQLiteOpenHelper dbHelper = (SQLiteOpenHelper) constructor.newInstance(mContext);
        db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        db.close();
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }
}
