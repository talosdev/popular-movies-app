package app.we.go.movies.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by apapad on 6/01/16.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "app.we.go.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAV = "favorite";


    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final String TABLE_ΝΑΜΕ = "favorites";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_POSTER_PATH = "poster_path";


        public static final String[] PROJECTION = new String[]{
                FavoriteMovieEntry._ID,
                FavoriteMovieEntry.COLUMN_MOVIE_ID,
                FavoriteMovieEntry.COLUMN_POSTER_PATH
        };

        // The projection above and the col indices below should always be in-sync

        public static final int COL_INDEX_ROW_ID = 0;
        public static final int COL_INDEX_MOVIE_ID = 1;
        public static final int COL_INDEX_POSTER_PATH = 2;


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();


        public static final String CONTENT_TYPE_DIR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;
        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;


        public static Uri buildFavoriteMovieUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }


        public static long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }


    }


}
