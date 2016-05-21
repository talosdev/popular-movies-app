package app.we.go.movies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.model.FavoriteMovie;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.MockTMDBServiceSync;
import app.we.go.movies.remote.TMDBService;

import static app.we.go.movies.remote.DummyData.DUMMY_MOVIE;
import static app.we.go.movies.remote.DummyData.INEXISTENT_MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_BACKDROP_PATH;
import static app.we.go.movies.remote.DummyData.MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static app.we.go.movies.remote.DummyData.MOVIE_POSTER_PATH;
import static app.we.go.movies.remote.DummyData.MOVIE_RELEASE_DATE_STR;
import static app.we.go.movies.remote.DummyData.MOVIE_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenterTest extends BasePresenterTest {


    @Mock
    MovieDetailsContract.View view;

    @Mock
    MovieDetailsContract.InfoView infoView;

    @Mock
    SharedPreferencesHelper sharedPreferencesHelper;

    @Mock
    FavoriteMovieDAO favoriteMovieDAO;

    private MovieDetailsPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // setup SharedPreferences mock
        when(sharedPreferencesHelper.formatDate(DummyData.MOVIE_RELEASE_DATE)).thenReturn(DummyData.MOVIE_RELEASE_DATE_STR);

        TMDBService service = new MockTMDBServiceSync();

        presenter = new MovieDetailsPresenter(service,
                sharedPreferencesHelper,
                favoriteMovieDAO);
        presenter.bindView(view);
        presenter.bindInfoView(infoView);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadMovieInfo() throws InterruptedException {

        presenter.loadMovieInfo(MOVIE_ID);

        verify(view).displayTitle(eq(MOVIE_TITLE));
        verify(view).displayImage(eq(MOVIE_BACKDROP_PATH));

        verify(infoView).displayInfo(DUMMY_MOVIE);
        verify(infoView).displayFormattedDate(MOVIE_RELEASE_DATE_STR);


        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(infoView);

    }

    @Test
    public void testLoadInfoWithWrongData() throws Exception {

        presenter.loadMovieInfo(INEXISTENT_MOVIE_ID);

        verifyError(view);
    }


    @Test
    public void testLoadInfoWithServerError() throws Exception {

        presenter.loadMovieInfo(MOVIE_ID_CAUSES_SERVER_ERROR);

        verifyFail(view);

    }


    @Test
    public void testCheckFavoriteTrue() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(MOVIE_ID)).thenReturn(true);

        presenter.checkFavorite(MOVIE_ID);
        verify(view).toggleFavorite(true);

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(infoView);

    }

    @Test
    public void testCheckFavoriteFalse() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(INEXISTENT_MOVIE_ID)).thenReturn(false);

        presenter.checkFavorite(INEXISTENT_MOVIE_ID);
        verify(view).toggleFavorite(false);

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(infoView);
    }


    @Test
    public void testFavoriteClickDisable() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(MOVIE_ID)).thenReturn(true);

        // We need to call checkFavorite first so that the "favorite" state is
        // set in the presenter
        presenter.checkFavorite(MOVIE_ID);
        verify(view).toggleFavorite(true);
        presenter.onFavoriteClick(MOVIE_ID, MOVIE_POSTER_PATH);
        verify(favoriteMovieDAO).delete(MOVIE_ID);
        verify(view).toggleFavorite(false);

        verifyNoMoreInteractions(view);
    }

    @Captor
    ArgumentCaptor<FavoriteMovie> argCaptor;

    @Test
    public void testFavoriteClickEnable() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(MOVIE_ID)).thenReturn(false);

        // We need to call checkFavorite first so that the "favorite" state is
        // set in the presenter
        presenter.checkFavorite(MOVIE_ID);
        verify(view).toggleFavorite(false);
        presenter.onFavoriteClick(MOVIE_ID, MOVIE_POSTER_PATH);
        verify(favoriteMovieDAO).put(argCaptor.capture());
        assertThat(argCaptor.getValue().getMovieId()).isEqualTo(MOVIE_ID);
        assertThat(argCaptor.getValue().getPosterPath()).isEqualTo(MOVIE_POSTER_PATH);

        verify(view).toggleFavorite(true);

        verifyNoMoreInteractions(view);
    }
}