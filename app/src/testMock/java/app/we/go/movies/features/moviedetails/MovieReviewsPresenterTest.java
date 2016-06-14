package app.we.go.movies.features.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.service.TMDBService;

import static app.we.go.movies.remote.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieReviewsPresenterTest extends BasePresenterTest {

    @Mock
    MovieDetailsContract.ReviewsView view;

    @Mock
    PresenterCache cache;

    MovieReviewsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TMDBService service = MockServiceModule.FakeTmdbServiceAsyncFactory.getInstance(true);

        presenter = new MovieReviewsPresenter(service, cache, "TAG");
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadReviews() throws Exception {
        presenter.loadMovieReviews(DummyData.MOVIE_ID_1);

        verify(view).displayReviews(eq(DummyData.REVIEWS.getReviews()));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testLoadReviewsWithWrongData() throws Exception {
        presenter.loadMovieReviews(DummyData.INEXISTENT_MOVIE_ID);

        verifyError(view);
    }

    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieReviews(MOVIE_ID_CAUSES_SERVER_ERROR);

        verifyFail(view);

    }
}