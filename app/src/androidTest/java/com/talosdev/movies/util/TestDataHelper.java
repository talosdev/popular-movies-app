package com.talosdev.movies.util;

import android.content.ContentValues;

import com.talosdev.movies.contract.MoviesContract;

/**
 * Created by apapad on 2016-01-15.
 */
public class TestDataHelper {


    public static final long TEST_MOVIE_ID = 12345L;

    public static final String TEST_MOVIE_POSTER_PATH = "/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg";

    public static ContentValues createMovieValues() {
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.FavoriteMovieEntry._ID, TEST_MOVIE_ID);
        cv.put(MoviesContract.FavoriteMovieEntry.COLUMN_POSTER_PATH, TEST_MOVIE_POSTER_PATH);
        return cv;
    }



}
