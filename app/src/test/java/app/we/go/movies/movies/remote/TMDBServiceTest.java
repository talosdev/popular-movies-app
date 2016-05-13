package app.we.go.movies.movies.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import app.we.go.movies.TestData;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBErrorParser;
import app.we.go.movies.remote.TMDBRetrofitService;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Service by contacting the real server.
 * Basically an integration test, as it also tests the json parsing part.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceTest {

    private static TMDBRetrofitService service;
    private static TMDBErrorParser errorParser;

    @BeforeClass
    public static void setUpClass() {
        // Get the real service instance from the dagger module
        ApplicationModule module = new ApplicationModule();
        Retrofit retrofit = module.provideRetrofit(module.provideGson(),
                module.provideOkHttpClient(new TMDBApiKeyInterceptor()));


        service = module.provideTMDBRetrofitService(retrofit);

        errorParser = module.provideTMDBErrorParser(retrofit);
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDetails() throws Exception {
        Call<Movie> call = service.getDetails(TestData.MOVIE_ID);
        Response<Movie> response = call.execute();

        assertThat(response.isSuccess()).isTrue();
        Movie m = response.body();

        assertThat(m.getOverview()).isEqualTo(TestData.MOVIE_OVERVIEW);
        assertThat(m.getTitle()).isEqualTo(TestData.MOVIE_TITLE);
        assertThat(m.getReleaseDate()).
                withDateFormat(new SimpleDateFormat(TMDB.DATE_FORMAT)).
                isEqualTo(TestData.MOVIE_RELEASE_DATE);
        assertThat(m.getVoteAverage()).isGreaterThan(0.0f).isLessThan(10.0f);
        assertThat(m.getVoteCount()).isGreaterThan(1L);
        assertThat(m.getPopularity()).isGreaterThan(0.0f);
        assertThat(m.getPosterPath()).isNotEmpty();
        assertThat(m.getBackdropPath()).isNotEmpty();
    }



    @Test
    public void testGetVideos() throws Exception {

        Call<VideoList> call = service.getVideos(TestData.VideoData.MOVIE_ID);
        Response<VideoList> response = call.execute();

        assertThat(response.isSuccess()).isTrue();
        VideoList body = response.body();
        List<Video> videos = body.getVideos();

        assertThat(videos.size()).isGreaterThanOrEqualTo(2);

        Video v1 = videos.get(0);
        assertThat(v1.getKey()).isEqualTo(TestData.VideoData.KEY_1);
        assertThat(v1.getName()).isEqualTo(TestData.VideoData.NAME_1);
        assertThat(v1.getType()).isEqualTo(TestData.VideoData.TYPE_1);

        Video v2 = videos.get(1);
        assertThat(v2.getKey()).isEqualTo(TestData.VideoData.KEY_2);
        assertThat(v2.getName()).isEqualTo(TestData.VideoData.NAME_2);
        assertThat(v2.getType()).isEqualTo(TestData.VideoData.TYPE_2);
    }

    @Test
    public void testGetReviews() throws Exception {
        Call<ReviewList> call = service.getReviews(TestData.ReviewData.MOVIE_ID);
        Response<ReviewList> response = call.execute();

        assertThat(response.isSuccess()).isTrue();
        ReviewList body = response.body();
        List<Review> reviews = body.getReviews();


        assertThat(reviews.size()).isGreaterThanOrEqualTo(2);

        Review r1 = reviews.get(0);
        assertThat(r1.getAuthor()).isEqualTo(TestData.ReviewData.AUTHOR_1);
        assertThat(r1.getContent()).isEqualTo(TestData.ReviewData.REVIEW_1);

        Review r2 = reviews.get(1);
        assertThat(r2.getAuthor()).isEqualTo(TestData.ReviewData.AUTHOR_2);
        assertThat(r2.getContent()).isEqualTo(TestData.ReviewData.REVIEW_2);
    }

    @Test
    public void testGetDetailsWithInexistentId() throws Exception {
        Call<Movie> call = service.getDetails(TestData.MOVIE_ID_INEXISTENT);
        Response<Movie> response = call.execute();

        assertThat(response.isSuccess()).isFalse();

        okhttp3.ResponseBody errorBody = response.errorBody();

        TMDBError error = errorParser.parse(errorBody);

        assertThat(error.getStatusCode()).isNotNull();
        assertThat(error.getStatusMessage()).isNotNull();
    }
}