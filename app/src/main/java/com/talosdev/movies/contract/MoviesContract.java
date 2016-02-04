package com.talosdev.movies.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * TODO This is WiP, I should modify it: database access and content provider
 * will only be used to support the favorite movies feature.
 * Created by apapad on 6/01/16.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.talosdev.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LIST = "list";
    public static final String PATH_DETAILS = "details";

    //TODO remove implements? no, I probably need it for the database part.
    public static final class MoviesListEntry implements BaseColumns {
       public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LIST).build();

       public static final String CONTENT_TYPE =
               ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LIST;
    }

    public static final class MovieEntry implements BaseColumns {
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


        public static final String TABLE_ΝΑΜΕ = "movie";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_VOTE_COUNT = "vote_count";


    }


    public static final class PopularityRankingEntry implements BaseColumns {

        public static final String TABLE_NAME = "popularity_ranking";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        // TODO, use _ID?
        public static final String COLUMN_RANK = "rank";
    }

    public static final class VoteRankingEntry implements BaseColumns {

        public static final String TABLE_NAME = "vote_ranking";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        // TODO, use _ID?
        public static final String COLUMN_RANK = "rank";
    }
}
