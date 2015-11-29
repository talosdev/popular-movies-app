package com.talosdev.movies.remote;

import android.test.AndroidTestCase;

import com.talosdev.movies.remote.json.Movie;

import org.junit.Before;

/**
 * Created by apapad on 26/11/15.
 */
public class MovieDetailsFetcherTest extends AndroidTestCase {

    private MovieDetailsFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new MovieDetailsFetcher();
    }


    /**
     * Contacts the API, and gets the details for a specific movie
     * @throws Exception
     */
    public void testFetchMovie() throws Exception {
        Movie movie = fetcher.fetch("206647");
        assertEquals("Spectre", movie.title);
        assertEquals(206647, movie.id);
    }
}


