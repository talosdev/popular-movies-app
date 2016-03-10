package app.we.go.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import app.we.go.movies.remote.json.Review;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 14/11/15.
 */
@RunWith(AndroidJUnit4.class)
public class MovieReviewsFetcherTest {

    private MovieReviewsFetcher fetcher;

    @Before
    public void setUp() {
        fetcher = new MovieReviewsFetcher(service);
    }


    /**
     * Contacts the API, gets the
     * @throws Exception
     */
    @Test
    public void testFetch() throws Exception {
        List<Review> reviews = fetcher.fetch(293660).reviews;

        assertThat(reviews.size()).isGreaterThanOrEqualTo(2);


    }


}