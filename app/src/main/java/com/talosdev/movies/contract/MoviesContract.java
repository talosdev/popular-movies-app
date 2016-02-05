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

    public static final String PATH_FAV = "favorite";


    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        public static final String CONTENT_TYPE_DIR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

    //    private static final String QUERY_POSTER_PATH = "posterPath";


        public static Uri buildFavoriteMovieUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

//        public static Uri buildFavoriteMovieUriWithPosterPath(long movieId, String posterPath) {
//            return ContentUris.withAppendedId(CONTENT_URI, movieId).
//                    buildUpon().
//                    appendQueryParameter(QUERY_POSTER_PATH, posterPath).
//                    build();
//        }

        public static long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }



        public static final String TABLE_ΝΑΜΕ = "favorites";

        // Only store the poster path in the db (and the movieId, as the id column)
        public static final String COLUMN_POSTER_PATH = "poster_path";


//        public static final String COLUMN_TITLE = "title";
//
//        public static final String COLUMN_OVERVIEW = "overview";
//
//        public static final String COLUMN_RELEASE_DATE = "release_date";
//
//        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
//
//        public static final String COLUMN_VOTE_COUNT = "vote_count";

    }


}
