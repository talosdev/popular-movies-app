package com.talosdev.movies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.contract.MoviesContract;
import com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;
import com.talosdev.movies.db.MovieDbHelper;

/**
 * Created by apapad on 6/01/16.
 */
public class MoviesProvider extends ContentProvider {

    public static UriMatcher uriMatcher = buildUriMatcher();


    public static final int FAVORITES_LIST = 100;
    public static final int FAVORITES_ITEM = 101;

    private MovieDbHelper movieDbHelper;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,
                MoviesContract.PATH_FAV,
                FAVORITES_LIST);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,
                MoviesContract.PATH_FAV + "/#",
                FAVORITES_ITEM);

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
            case FAVORITES_LIST:
                return FavoriteMovieEntry.CONTENT_TYPE_DIR;
            case FAVORITES_ITEM:
                return FavoriteMovieEntry.CONTENT_TYPE_ITEM;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(Tags.DB, "Insert favorite: " + uri);

        switch (uriMatcher.match(uri)) {
            case FAVORITES_LIST:
                return null;
            case FAVORITES_ITEM:
                // TODO
                return insertFavoriteMovie(FavoriteMovieEntry.getMovieIdFromUri(uri),
                        values);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private Uri insertFavoriteMovie(long movieIdFromUri, ContentValues values) {

        values.put(FavoriteMovieEntry.COLUMN_MOVIE_ID, movieIdFromUri);

        SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        long rowId = database.insert(FavoriteMovieEntry.TABLE_ΝΑΜΕ, null, values);

        if (rowId == -1) {
            return null;
        } else {
            return FavoriteMovieEntry.buildFavoriteMovieUri(movieIdFromUri);
        }

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(Tags.DB, "Query favorites: " + uri);
        Cursor cursor;
        SQLiteDatabase database = movieDbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case FAVORITES_LIST:
                cursor = database.query(FavoriteMovieEntry.TABLE_ΝΑΜΕ,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITES_ITEM:
                cursor = checkIsFavorite(FavoriteMovieEntry.getMovieIdFromUri(uri));
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // TODO do I need this?
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    private Cursor checkIsFavorite(long movieId) {
        SQLiteDatabase database = movieDbHelper.getReadableDatabase();
        return database.query(FavoriteMovieEntry.TABLE_ΝΑΜΕ,
                null,
                FavoriteMovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{movieId+""},
                null,
                null,
                null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(Tags.DB, "Delete favorite: " + uri);

        switch (uriMatcher.match(uri)) {
            case FAVORITES_LIST:
                return 0;
            case FAVORITES_ITEM:
                return removeFavorite(FavoriteMovieEntry.getMovieIdFromUri(uri));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private int removeFavorite(long movieId) {
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        return database.delete(FavoriteMovieEntry.TABLE_ΝΑΜΕ,
                FavoriteMovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[] {movieId + ""});
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
