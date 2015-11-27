package talosdev.movies.remote;

import android.test.AndroidTestCase;

import org.junit.Before;

import talosdev.movies.remote.json.Movie;

/**
 * Created by apapad on 26/11/15.
 */
public class MovieDetailsFetcherTest extends AndroidTestCase {

    private MovieDetailsFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new MovieDetailsFetcher();
    }

    public void testFetchMovie() throws Exception {
       // assertEquals("https://api.themoviedb.org/3/movie/", TMDB.URL_MOVIE_DETAILS);

        Movie movie = fetcher.fetch("206647");
        assertEquals("Spectre", movie.title);
        assertEquals(206647, movie.id);

    }
}


