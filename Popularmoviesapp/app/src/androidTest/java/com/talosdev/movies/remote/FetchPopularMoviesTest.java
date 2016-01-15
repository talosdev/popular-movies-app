package com.talosdev.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.json.Movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 14/11/15.
 */
@RunWith(AndroidJUnit4.class)
public class FetchPopularMoviesTest {

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
        List<Movie> movies = fetcher.fetch(SortByCriterion.POPULARITY).movies;

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
    public void testFetchMoviesVote() throws Exception {
        List<Movie> movies = fetcher.fetch(SortByCriterion.VOTE).movies;

        assertThat(movies).hasSize(20);
        for (int i=0; i<19; i++) {
            assertThat(movies.get(i).voteAverage).
                    isGreaterThanOrEqualTo(movies.get(i + 1).voteAverage);
        }
    }


}