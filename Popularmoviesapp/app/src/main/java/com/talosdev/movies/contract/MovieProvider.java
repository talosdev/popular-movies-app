package com.talosdev.movies.contract;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by apapad on 6/01/16.
 */
public class MovieProvider extends ContentProvider {

    public static UriMatcher uriMatcher = buildUriMatcher();


    private static final int MOVIES_LIST = 100;
    private static final int MOVIE_DETAILS = 200;



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
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = uriMatcher.match(uri);

        switch (match) {

            case MOVIES_LIST:
                return MoviesContract.MoviesListEntry.CONTENT_TYPE;
            case MOVIE_DETAILS:
                return MoviesContract.MovieDetailsEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
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
