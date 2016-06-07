package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.helpers.SharedPreferencesHelper;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.service.TMDBService;

import static app.we.go.movies.remote.DummyData.DUMMY_MOVIE;
import static app.we.go.movies.remote.DummyData.INEXISTENT_MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_ID;
import static app.we.go.movies.remote.DummyData.MOVIE_ID_CAUSES_SERVER_ERROR;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieInfoPresenterTest extends BasePresenterTest {


    @Mock
    MovieDetailsContract.InfoView view;

    @Mock
    SharedPreferencesHelper sharedPreferencesHelper;

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

    @NonNull
    private MovieInfoPresenter getPresenter(long movieId) {
        MovieInfoPresenter presenter =
                new MovieInfoPresenter(service, service.getDetails(movieId),
                        sharedPreferencesHelper,
                        cache,
                        "TAG");
        presenter.bindView(view);
        return presenter;
    }

    @Test
    public void testLoadMovieInfo() throws InterruptedException {

        MovieInfoPresenter presenter = getPresenter(MOVIE_ID);

        presenter.loadMovieInfo();

        verify(view).displayInfo(DUMMY_MOVIE);
        verify(view).displayFormattedDate(DummyData.MOVIE_RELEASE_DATE_STR);

        verifyNoMoreInteractions(view);
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



}