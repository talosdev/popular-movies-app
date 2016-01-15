package com.talosdev.movies.util;

import android.content.ContentValues;

import com.talosdev.movies.contract.MoviesContract;

/**
 * Created by apapad on 2016-01-15.
 */
public class TestDataHelper {


    public static final long TEST_MOVIE_ID = 12345L;
    public static final String TEST_MOVIE_TITLE = "Test movie title !";
    public static final String TEST_MOVIE_OVERVIEW = "Test movie overview. A boy suddenly realizes that....";
    public static final String TEST_MOVIE_RELEASE_DATE = "2013/01/01";
    public static final double TEST_MOVIE_VOTE_AVERAGE = 7.6;
    public static final int TEST_MOVIE_VOTE_COUNT = 1000;

    public static ContentValues createMovieValues() {
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MovieEntry._ID, TEST_MOVIE_ID);
        cv.put(MoviesContract.MovieEntry.COLUMN_TITLE, TEST_MOVIE_TITLE);
        cv.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, TEST_MOVIE_OVERVIEW);
        cv.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, TEST_MOVIE_RELEASE_DATE);
        cv.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, TEST_MOVIE_VOTE_AVERAGE);
        cv.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, TEST_MOVIE_VOTE_COUNT);

        return cv;
    }



}
