package talosdev.movies.remote;

import android.test.AndroidTestCase;

import org.junit.Before;

import java.util.List;

import talosdev.movies.data.SortByCriterion;
import talosdev.movies.remote.json.Movie;

/**
 * Created by apapad on 14/11/15.
 */
public class FetchPopularMoviesTest extends AndroidTestCase{

    private PopularMoviesFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new PopularMoviesFetcher();
    }

    public void testFetchMoviesPopularity() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.POPULARITY).movies;

        assertEquals(20, movies.size());
        for (int i=0; i<19; i++) {
            assertTrue(movies.get(i).popularity > 0);

            // TODO: enable this when the api is fixed, currently doesn't return ordered results
            //assertTrue(movies.get(i).popularity >= movies.get(i+1).popularity);
        }
    }

    public void testFetchMoviesVote() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.VOTE).movies;

        assertEquals(20, movies.size());
        for (int i=0; i<19; i++) {
            assertTrue(movies.get(i).voteAverage >= movies.get(i+1).voteAverage);
        }
    }


}