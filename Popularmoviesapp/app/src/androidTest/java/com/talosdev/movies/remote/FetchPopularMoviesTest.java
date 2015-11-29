package com.talosdev.movies.remote;

import android.test.AndroidTestCase;

import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.json.Movie;

import org.junit.Before;

import java.util.List;

/**
 * Created by apapad on 14/11/15.
 */
public class FetchPopularMoviesTest extends AndroidTestCase{

    private PopularMoviesFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new PopularMoviesFetcher();
    }


    /**
     * Contacts the API, gets the popular movies ordered by popularity and checks
     * that they are ordered as expected
     * @throws Exception
     */
    public void testFetchMoviesPopularity() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.POPULARITY).movies;

        assertEquals(20, movies.size());
        for (int i=0; i<19; i++) {
            assertTrue(movies.get(i).popularity > 0);

            // There seems to be a bug in the TMDB api
            // TODO: enable this when the api is fixed, currently doesn't return ordered results
            //assertTrue(movies.get(i).popularity >= movies.get(i+1).popularity);
        }
    }

    /**
     * Contacts the API, gets the popular movies ordered by vote and checks
     * that they are ordered as expected
     * @throws Exception
     */
    public void testFetchMoviesVote() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.VOTE).movies;

        assertEquals(20, movies.size());
        for (int i=0; i<19; i++) {
            assertTrue(movies.get(i).voteAverage >= movies.get(i+1).voteAverage);
        }
    }


}