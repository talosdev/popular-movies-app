package app.we.go.movies.contract;

import android.provider.BaseColumns;

/**
 * Created by apapad on 6/01/16.
 */
public class MoviesContract {


    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final String TABLE_ΝΑΜΕ = "favorites";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_POSTER_PATH = "poster_path";


        public static final int COL_INDEX_MOVIE_ID = 1;
        public static final int COL_INDEX_POSTER_PATH = 2;



    }


}
