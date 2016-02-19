package app.we.go.movies.db;

import app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by apapad on 2016-01-14.
 */
public class MovieDbHelperTest extends AbstractSchemaValidatorATC<MovieDbHelper> {

    private  final String TAG = "TEST";


    @Override
    public String getDatabaseName() {
        return MovieDbHelper.DATABASE_NAME;
    }

    @Override
    public Class<MovieDbHelper> getDbHelperClass() {
        return MovieDbHelper.class;
    }


    @Override
    protected Map<String, Set<String>> getSchemaHashMap() {
        Map<String, Set<String>> map = new HashMap<>();

        Set<String> movieColumnsSet = new HashSet<>();
        movieColumnsSet.add(FavoriteMovieEntry._ID);
        movieColumnsSet.add(FavoriteMovieEntry.COLUMN_MOVIE_ID);
        movieColumnsSet.add(FavoriteMovieEntry.COLUMN_POSTER_PATH);


        map.put(FavoriteMovieEntry.TABLE_ΝΑΜΕ, movieColumnsSet);

        return map;
    }

    // Need to override the tests from the parent class, because
    // otherwise I cannot run this test case individually from
    // within Android Studio.

    @Override
    @Test
    public void testTablesExist() throws Exception {
        super.testTablesExist();
    }

    @Override
    @Test
    public void testTableColumns() throws Exception {
        super.testTableColumns();
    }
}