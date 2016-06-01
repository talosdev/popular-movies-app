package app.we.go.movies.db;

import android.provider.BaseColumns;

/**
 * Created by apapad on 6/01/16.
 */
public interface DatabaseContract {


    interface FavoriteMoviesTable extends BaseColumns{
        String TABLE_ΝΑΜΕ = "favorites";
        String COLUMN_MOVIE_ID = "movie_id";
        String COLUMN_POSTER_PATH = "poster_path";


        int COL_INDEX_MOVIE_ID = 1;
        int COL_INDEX_POSTER_PATH = 2;
    }


}
