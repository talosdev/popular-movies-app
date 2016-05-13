package app.we.go.movies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.DummyData;
import app.we.go.movies.R;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;

import static app.we.go.movies.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class VideosPresenterTest {

    @Mock
    MovieDetailsContract.VideosView view;

    @Mock
    URLBuilder urlBuilder;


    MovieVideosPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TMDBService service = new MockTMDBService();

        presenter = new MovieVideosPresenter(service, urlBuilder);
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadVideos() throws Exception {
        presenter.loadMovieVideos(DummyData.DUMMY_MOVIE_ID);

        verify(view).displayVideos(eq(DummyData.VIDEOS.getVideos()));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testLoadReviewsWithWrongData() throws Exception {
        presenter.loadMovieVideos(DummyData.INEXISTENT_MOVIE_ID);

        verify(view).displayError(R.string.error_network);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieVideos(MOVIE_ID_CAUSES_SERVER_ERROR);

        verify(view).displayError(R.string.error_network);
        verifyNoMoreInteractions(view);

    }
}