package com.talosdev.movies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.talosdev.movies.contract.MoviesContract;
import com.talosdev.movies.db.MovieDbHelper;

/**
 * Created by apapad on 6/01/16.
 */
public class MoviesProvider extends ContentProvider {

    public static UriMatcher uriMatcher = buildUriMatcher();


    public static final int MOVIES_LIST = 100;
    public static final int MOVIE_DETAILS = 200;
    private MovieDbHelper movieDbHelper;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,
                        MoviesContract.PATH_LIST,
                        MOVIES_LIST);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,
                MoviesContract.PATH_DETAILS + "/#",
                MOVIE_DETAILS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = uriMatcher.match(uri);

        switch (match) {

            case MOVIES_LIST:
                return MoviesContract.MoviesListEntry.CONTENT_TYPE;
            case MOVIE_DETAILS:
                return MoviesContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch(uriMatcher.match(uri)) {
            case MOVIES_LIST:
                cursor = getMovieList();
                break;
            case MOVIE_DETAILS:
                cursor = getMovieDetails(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Cursor getMovieDetails(Uri uri, String[] projection, String sortOrder) {
        return  movieDbHelper.getReadableDatabase().query(
                MoviesContract.MovieEntry.TABLE_ΝΑΜΕ,
                projection,
                MoviesContract.MovieEntry._ID + " = ?",
                new String[]{MoviesContract.MovieEntry.getMovieIdFromUri(uri) + ""},
                sortOrder,
                null,
                null);
    }

    private Cursor getMovieList() {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
