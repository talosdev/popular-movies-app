package com.talosdev.movies.db;

import com.talosdev.movies.contract.MoviesContract.MovieEntry;
import com.talosdev.movies.contract.MoviesContract.PopularityRankingEntry;

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
        movieColumnsSet.add(MovieEntry._ID);
        movieColumnsSet.add(MovieEntry.COLUMN_TITLE);
        movieColumnsSet.add(MovieEntry.COLUMN_OVERVIEW);
        movieColumnsSet.add(MovieEntry.COLUMN_RELEASE_DATE);
        movieColumnsSet.add(MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumnsSet.add(MovieEntry.COLUMN_VOTE_COUNT);

        map.put(MovieEntry.TABLE_ΝΑΜΕ, movieColumnsSet);


        Set<String> popRankingColumns = new HashSet<>();
        popRankingColumns.add(PopularityRankingEntry._ID);
        popRankingColumns.add(PopularityRankingEntry.COLUMN_MOVIE_ID);

        map.put(PopularityRankingEntry.TABLE_NAME, popRankingColumns);

        return map;
    }


}