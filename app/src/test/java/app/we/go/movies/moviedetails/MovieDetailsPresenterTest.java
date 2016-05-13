package app.we.go.movies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.remote.TMDBService;

import static app.we.go.movies.DummyData.DUMMY_MOVIE;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_BACKDROP_PATH;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_ID;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_TITLE;
import static app.we.go.movies.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenterTest {


    @Mock
    MovieDetailsContract.View view;

    @Mock
    MovieDetailsContract.InfoView infoView;

    private MovieDetailsPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        TMDBService service = new MockTMDBService();

        presenter = new MovieDetailsPresenter(service);
        presenter.bindView(view);
        presenter.bindInfoView(infoView);


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadMovieInfo() throws InterruptedException {

        presenter.loadMovieInfo(DUMMY_MOVIE_ID);

        verify(view).displayTitle(eq(DUMMY_MOVIE_TITLE));
        verify(view).displayImage(eq(DUMMY_MOVIE_BACKDROP_PATH));

        verify(infoView).displayInfo(DUMMY_MOVIE);

    }

    @Test
    public void testLoadInfoWithWrongData() throws Exception {
//
//        presenter.loadMovieInfo(INEXISTENT_MOVIE_ID);
//
//        verify(view).displayError(anyInt());

    }


    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieInfo(MOVIE_ID_CAUSES_SERVER_ERROR);

        verify(view).displayError(anyInt());

    }


    // TODO test chackFavorit, onFavoriteClick

}