package app.we.go.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.we.go.movies.remote.json.Movie;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(movie.title).isEqualTo("Spectre");
        assertThat(movie.id).isEqualTo(206647);
    }
}


