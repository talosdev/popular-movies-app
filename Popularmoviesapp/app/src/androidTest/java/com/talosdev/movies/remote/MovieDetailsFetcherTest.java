package com.talosdev.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import com.talosdev.movies.remote.json.Movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by apapad on 26/11/15.
 */
@RunWith(value = AndroidJUnit4.class)
public class MovieDetailsFetcherTest  {

    private MovieDetailsFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new MovieDetailsFetcher();
    }


    /**
     * Contacts the API, and gets the details for a specific movie
     * @throws Exception
     */
    @Test
    public void testFetchMovie() throws Exception {
        Movie movie = fetcher.fetch(206647);
        assertEquals("Spectre", movie.title);
        assertEquals(206647, movie.id);
    }
}


