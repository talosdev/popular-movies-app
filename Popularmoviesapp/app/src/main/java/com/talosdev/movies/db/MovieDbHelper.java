package com.talosdev.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.talosdev.movies.contract.MoviesContract.MovieEntry;
import com.talosdev.movies.contract.MoviesContract.PopularityRankingEntry;

/**
 * Created by apapad on 2016-01-{DAY}.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "pop_movies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_ΝΑΜΕ + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                MovieEntry.COLUMN_RELEASE_DATE + " DATE NOT NULL," +
                MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL" +
                ");";


        final String SQL_CREATE_POPULARITY_RANKING_TABLE =
                "CREATE TABLE " + PopularityRankingEntry.TABLE_NAME + " (" +
                PopularityRankingEntry._ID + " INTEGER NOT NULL," +
                PopularityRankingEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL" +
                ");";



        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_POPULARITY_RANKING_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
