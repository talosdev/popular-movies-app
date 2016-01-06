package com.talosdev.movies.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by apapad on 6/01/16.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.talosdev.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LIST = "list";
    public static final String PATH_DETAILS = "details";

    //TODO remove implements?
    public static final class MoviesListEntry implements BaseColumns {
       public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LIST).build();

       public static final String CONTENT_TYPE =
               ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LIST;
    }

    public static final class MovieDetailsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAILS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LIST;

        public static Uri buildDetailsUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        //TODO test this
        public static long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

    }



}
