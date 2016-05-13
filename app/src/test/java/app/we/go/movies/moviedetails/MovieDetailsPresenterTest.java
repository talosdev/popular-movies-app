package app.we.go.movies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.DummyData;
import app.we.go.movies.R;
import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.remote.TMDBService;

import static app.we.go.movies.DummyData.DUMMY_MOVIE;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_BACKDROP_PATH;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_DATE_STR;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_ID;
import static app.we.go.movies.DummyData.DUMMY_MOVIE_TITLE;
import static app.we.go.movies.DummyData.INEXISTENT_MOVIE_ID;
import static app.we.go.movies.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenterTest {


    @Mock
    MovieDetailsContract.View view;

    @Mock
    MovieDetailsContract.InfoView infoView;

    @Mock
    SharedPreferencesHelper sharedPreferencesHelper;

    private MovieDetailsPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(sharedPreferencesHelper.formatDate(DummyData.DUMMY_MOVIE_DATE)).thenReturn(DummyData.DUMMY_MOVIE_DATE_STR);


        TMDBService service = new MockTMDBService();

        presenter = new MovieDetailsPresenter(service, sharedPreferencesHelper);
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
        verify(infoView).displayFormattedDate(DUMMY_MOVIE_DATE_STR);


        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(infoView);

    }

    @Test
    public void testLoadInfoWithWrongData() throws Exception {

        presenter.loadMovieInfo(INEXISTENT_MOVIE_ID);

        verify(view).displayError(R.string.error_network);
        verifyNoMoreInteractions(view);
    }


    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieInfo(MOVIE_ID_CAUSES_SERVER_ERROR);

        verify(view).displayError(anyInt());
        verifyNoMoreInteractions(view);

    }


    // TODO test chackFavorit, onFavoriteClick

}