package app.we.go.movies.movies.remote;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import app.we.go.movies.remote.json.MovieReviewsJSONParser;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 1/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieReviewsJSONParserTest {
    private String json = "{\"id\":293660,\"page\":1,\"results\":[{\"id\":\"56c146cac3a36817f900d5f0\",\"author\":\"huy.duc.eastagile\",\"content\":\"A funny movie with a romantic love story.\\r\\n\\r\\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \\r\\n\\r\\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.\",\"url\":\"http://j.mp/1ohFuvI\"},{\"id\":\"56ca035a9251414a7a0062f0\",\"author\":\"Wong\",\"content\":\"I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.\",\"url\":\"http://j.mp/1RVzjsp\"}],\"total_pages\":1,\"total_results\":2}";
    public static final String REVIEW_1 = "A funny movie with a romantic love story.\r\n\r\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \r\n\r\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.";
    public static final String REVIEW_2 = "I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.";
    public static final String AUTHOR_1 = "huy.duc.eastagile";
    public static final String AUTHOR_2 = "Wong";


    private static MovieReviewsJSONParser parser;

    @BeforeClass
    public static void setUp() {
        parser = new MovieReviewsJSONParser();
    }


    @Test
    public void testParseReviews() throws Exception {
        ReviewList reviewList = parser.parse(json);
        List<Review> reviews = reviewList.reviews;

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviewList.page).isEqualTo(1);

        Review firstReview = reviews.get(0);
        assertThat(firstReview.content).isEqualTo(REVIEW_1);
        assertThat(firstReview.author).isEqualTo(AUTHOR_1);

        Review secondReview = reviews.get(1);
        assertThat(secondReview.content).isEqualTo(REVIEW_2);
        assertThat(secondReview.author).isEqualTo(AUTHOR_2);
    }
}