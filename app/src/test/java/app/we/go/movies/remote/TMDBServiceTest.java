package app.we.go.movies.remote;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.text.SimpleDateFormat;
import java.util.List;

import app.we.go.movies.TestData;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.ServiceModule;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;
import app.we.go.movies.util.RxTestingUtils;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Service by contacting the real server.
 * Basically an integration test, as it also tests the json parsing part.
 * <p/>
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceTest {

    private static TMDBRetrofitService service;
    private static TMDBErrorParser errorParser;

    @Mock
    Context context;

    @BeforeClass
    public static void setUpClass() {
        // Get the real service instance from the dagger module
        ServiceModule module = new ServiceModule();
        ApplicationModule appModule = new ApplicationModule();
        Retrofit retrofit = module.provideRetrofit(appModule.provideGson(),
                module.provideOkHttpClient(new TMDBApiKeyInterceptor()),
                // important, we use the default call adapter factory,
                // and not the io scheduler, because we want this to be sync.
                RxJavaCallAdapterFactory.create());


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
        Observable<Response<Movie>> movieDetailsObservable = service.getDetails(TestData.MOVIE_ID);

        TestSubscriber<Response<Movie>> testSubscriber = new TestSubscriber<>();

        movieDetailsObservable.
                subscribe(testSubscriber);

        Response<Movie> response = RxTestingUtils.getUniqueOnNextEvent(testSubscriber);

        assertThat(response.isSuccessful()).isTrue();

        Movie m = response.body();

        assertThat(m.getOverview()).isEqualTo(TestData.MOVIE_OVERVIEW);
        assertThat(m.getTitle()).isEqualTo(TestData.MOVIE_TITLE);
        assertThat(m.getReleaseDate()).
                withDateFormat(new SimpleDateFormat(TMDB.DATE_FORMAT)).
                isEqualTo(TestData.MOVIE_RELEASE_DATE_STR);
        assertThat(m.getVoteAverage()).isGreaterThan(0.0f).isLessThan(10.0f);
        assertThat(m.getVoteCount()).isGreaterThan(1L);
        assertThat(m.getPopularity()).isGreaterThan(0.0f);
        assertThat(m.getPosterPath()).isNotEmpty();
        assertThat(m.getBackdropPath()).isNotEmpty();

    }



    @Test
    public void testGetVideos() throws Exception {

        Observable<Response<VideoList>> videosObservable = service.getVideos(TestData.VideoData.MOVIE_ID);

        TestSubscriber<Response<VideoList>> testSubscriber = new TestSubscriber<>();

        videosObservable.subscribe(testSubscriber);

        Response<VideoList> response = RxTestingUtils.getUniqueOnNextEvent(testSubscriber);

        assertThat(response.isSuccessful());

        List<Video> videos = response.body().getVideos();

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
        Observable<Response<ReviewList>> reviewsObservable = service.getReviews(TestData.ReviewData.MOVIE_ID);

        TestSubscriber<Response<ReviewList>> testSubscriber = new TestSubscriber<>();

        reviewsObservable.subscribe(testSubscriber);
        Response<ReviewList> response = RxTestingUtils.getUniqueOnNextEvent(testSubscriber);

        assertThat(response.isSuccessful()).isTrue();

        List<Review> reviews = response.body().getReviews();

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
        Observable<Response<Movie>> movieDetailsObservable = service.getDetails(TestData.MOVIE_ID_INEXISTENT);
        TestSubscriber<Response<Movie>> testSubscriber = new TestSubscriber<>();

        movieDetailsObservable.subscribe(testSubscriber);

        Response<Movie> response = RxTestingUtils.getUniqueOnNextEvent(testSubscriber);

        assertThat(response.isSuccessful()).isFalse();


        TMDBError error = errorParser.parse(response.errorBody());

        assertThat(error.getStatusCode()).isNotNull();
        assertThat(error.getStatusMessage()).isNotNull();
    }
}