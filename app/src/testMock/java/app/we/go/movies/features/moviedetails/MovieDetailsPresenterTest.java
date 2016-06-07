package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.helpers.SharedPreferencesHelper;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.service.TMDBService;

import static app.we.go.movies.remote.DummyData.INEXISTENT_MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_BACKDROP_PATH;
import static app.we.go.movies.remote.DummyData.MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static app.we.go.movies.remote.DummyData.MOVIE_POSTER_PATH;
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
    MovieDetailsContract.DetailsView view;

    @Mock
    SharedPreferencesHelper sharedPreferencesHelper;

    @Mock
    FavoriteMovieDAO favoriteMovieDAO;

    @Mock
    PresenterCache cache;

    private TMDBService service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // setup SharedPreferences mock
        when(sharedPreferencesHelper.formatDate(DummyData.MOVIE_RELEASE_DATE)).thenReturn(DummyData.MOVIE_RELEASE_DATE_STR);

        service = MockServiceModule.FakeTmdbServiceAsyncFactory.getInstance(true);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadMovieInfo() throws InterruptedException {

        MovieDetailsPresenter presenter = getPresenter(MOVIE_ID);

        presenter.loadMovieInfo();

        verify(view).displayTitle(eq(MOVIE_TITLE));
        verify(view).displayImage(eq(MOVIE_BACKDROP_PATH));


        verifyNoMoreInteractions(view);

    }

    @NonNull
    private MovieDetailsPresenter getPresenter(long movieId) {
        MovieDetailsPresenter presenter =
                new MovieDetailsPresenter(service, service.getDetails(movieId),
                favoriteMovieDAO,
                cache,
                "TAG");
        presenter.bindView(view);
        return presenter;
    }

    @Test
    public void testLoadInfoWithWrongData() throws Exception {

        getPresenter(INEXISTENT_MOVIE_ID).loadMovieInfo();

        verifyError(view);
    }


    @Test
    public void testLoadInfoWithServerError() throws Exception {
        getPresenter(MOVIE_ID_CAUSES_SERVER_ERROR).loadMovieInfo();

        verifyFail(view);
    }


    @Test
    public void testCheckFavoriteTrue() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(MOVIE_ID)).thenReturn(true);

        getPresenter(MOVIE_ID).checkFavorite(MOVIE_ID);

        verify(view).toggleFavorite(true);

        verifyNoMoreInteractions(view);

    }

    @Test
    public void testCheckFavoriteFalse() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(INEXISTENT_MOVIE_ID)).thenReturn(false);

        getPresenter(INEXISTENT_MOVIE_ID).checkFavorite(INEXISTENT_MOVIE_ID);
        verify(view).toggleFavorite(false);

        verifyNoMoreInteractions(view);
    }


    @Test
    public void testFavoriteClickDisable() throws Exception {
        // setup DAO mock
        when(favoriteMovieDAO.get(MOVIE_ID)).thenReturn(true);
        MovieDetailsContract.DetailsPresenter presenter = getPresenter(MOVIE_ID);

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
        MovieDetailsContract.DetailsPresenter presenter = getPresenter(MOVIE_ID);

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