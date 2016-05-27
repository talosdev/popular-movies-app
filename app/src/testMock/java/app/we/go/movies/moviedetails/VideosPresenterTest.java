package app.we.go.movies.moviedetails;

import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.dependency.FakeTMDBServiceSync;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;

import static app.we.go.movies.remote.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class VideosPresenterTest extends BasePresenterTest {

    @Mock
    MovieDetailsContract.VideosView view;

    @Mock
    URLBuilder urlBuilder;

    @Mock
    Uri uri;

    public static final String VIDEO_KEY = "key";

    private static final String VIDEO_NAME = "video_name";

    MovieVideosPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(urlBuilder.buildYoutubeUri(VIDEO_KEY)).thenReturn(uri);


        TMDBService service = new FakeTMDBServiceSync();

        presenter = new MovieVideosPresenter(service, urlBuilder);
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadVideos() throws Exception {
        presenter.loadMovieVideos(DummyData.MOVIE_ID);

        verify(view).displayVideos(eq(DummyData.VIDEOS.getVideos()));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testLoadVideosWithWrongData() throws Exception {
        presenter.loadMovieVideos(DummyData.INEXISTENT_MOVIE_ID);

        verifyError(view);
    }

    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieVideos(MOVIE_ID_CAUSES_SERVER_ERROR);

        verifyFail(view);

    }

    @Test
    public void testOnVideoClicked() throws Exception {
        presenter.onVideoClicked(VIDEO_KEY);

        verify(view).openVideo(uri);
        verifyNoMoreInteractions(view);
    }


    @Test
    public void testOnVideoShareClicked() throws Exception {
        presenter.onShareVideoClicked(VIDEO_KEY, VIDEO_NAME);

        verify(view).shareVideo(uri, VIDEO_NAME);
        verifyNoMoreInteractions(view);

    }
}