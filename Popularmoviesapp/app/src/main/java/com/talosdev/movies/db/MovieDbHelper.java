package com.talosdev.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;

/**
 * TODO This is WiP, I should modify it: database access and content provider
 * will only be used to support the favorite movies feature.
 * Created by apapad on 2016-01-??.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "pop_movies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITES_TABLE =
                "CREATE TABLE " + FavoriteMovieEntry.TABLE_ΝΑΜΕ + " (" +
                        FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY, " +
                        FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL" +
                        ");";

        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
