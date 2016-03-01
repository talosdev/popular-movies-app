package app.we.go.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.Movie;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 14/11/15.
 */
@RunWith(AndroidJUnit4.class)
public class PopularMoviesFetcherTest {

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
    @Test
    public void testFetchMoviesPopularity() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.POPULARITY, 1).movies;

        assertThat(movies).hasSize(20);
        for (int i=0; i<19; i++) {
            assertThat(movies.get(i).popularity).isGreaterThan(0);

            // There seems to be a bug in the TMDB api
            // TODO: enable this when the api is fixed, currently doesn't return ordered results
            //assertThat(movies.get(i).popularity).isGreaterThan(movies.get(i+1).popularity);
        }
    }

    /**
     * Contacts the API, gets the popular movies ordered by vote and checks
     * that they are ordered as expected
     * @throws Exception
     */
    @Test
    public void testFetchMoviesVote() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.VOTE, 1).movies;

        assertThat(movies).hasSize(20);
        for (int i=0; i<19; i++) {
            assertThat(movies.get(i).voteAverage).
                    isGreaterThanOrEqualTo(movies.get(i + 1).voteAverage);
        }
    }


    /**
     * Gets two different pages and makes sure they are distinct
     * @throws Exception
     */
    @Test
    public void testFetchWithPaging() throws Exception {
        List<Movie> movies1 = fetcher.fetch(SortByCriterion.POPULARITY, 1).movies;
        assertThat(movies1).hasSize(20);

        List<Movie> movies2 = fetcher.fetch(SortByCriterion.POPULARITY, 2).movies;
        assertThat(movies2).hasSize(20);

        assertThat(movies1).doesNotContainAnyElementsOf(movies2);


    }
}