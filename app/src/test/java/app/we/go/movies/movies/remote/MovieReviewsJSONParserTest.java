package app.we.go.movies.movies.remote;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import app.we.go.movies.TestData;
import app.we.go.movies.remote.json.MovieReviewsJSONParser;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 1/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieReviewsJSONParserTest {


    private static MovieReviewsJSONParser parser;

    @BeforeClass
    public static void setUp() {
        parser = new MovieReviewsJSONParser();
    }


    @Test
    public void testParseReviews() throws Exception {
        ReviewList reviewList = parser.parse(TestData.ReviewData.JSON);
        List<Review> reviews = reviewList.getReviews();

        assertThat(reviews.size()).isEqualTo(2);
        //assertThat(reviewList.getPage()).isEqualTo(1);

        Review firstReview = reviews.get(0);
        assertThat(firstReview.getContent()).isEqualTo(TestData.ReviewData.REVIEW_1);
        assertThat(firstReview.getAuthor()).isEqualTo(TestData.ReviewData.AUTHOR_1);

        Review secondReview = reviews.get(1);
        assertThat(secondReview.getContent()).isEqualTo(TestData.ReviewData.REVIEW_2);
        assertThat(secondReview.getAuthor()).isEqualTo(TestData.ReviewData.AUTHOR_2);
    }

    @Test
    public void testBackAndForth() throws Exception {
        ReviewList reviewList = parser.parse(TestData.ReviewData.JSON);
        String newJson = parser.toJson(reviewList);
        ReviewList parsedReviewList = parser.parse(newJson);
        assertThat(parsedReviewList.getReviews()).containsOnlyElementsOf(reviewList.getReviews());

    }
}