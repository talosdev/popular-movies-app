package com.talosdev.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;

/**
 * Created by apapad on 2016-01-??.
 */
public class MovieDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "pop_movies.db";

    public static final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoriteMovieEntry.TABLE_ΝΑΜΕ + " (" +
            FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY, " +
            FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL" +
            ");";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFavoritesTable(db);
    }

    private void createFavoritesTable(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // create favorites tables, which was not available in v1
            createFavoritesTable(db);
        }
    }
}