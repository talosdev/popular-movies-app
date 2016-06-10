package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.db.RxInMemoryFavoriteMoviesDAO;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.mvp.BasePresenterTest;
import app.we.go.movies.remote.service.TMDBService;
import rx.observers.TestSubscriber;

import static app.we.go.movies.remote.DummyData.MOVIE_ID_1;
import static app.we.go.movies.remote.DummyData.MOVIE_POSTER_PATH_1;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenterFavoritesTest extends BasePresenterTest {


    @Mock
    MovieDetailsContract.DetailsView view;

    RxFavoriteMovieDAO dao;
    @Mock
    PresenterCache cache;

    private TMDBService service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = MockServiceModule.FakeTmdbServiceAsyncFactory.getInstance(true);

        // reset the dao for every test
        dao = new RxInMemoryFavoriteMoviesDAO();

    }


    @NonNull
    private MovieDetailsPresenter getPresenter(long movieId) {
        MovieDetailsPresenter presenter =
                new MovieDetailsPresenter(service, service.getDetails(movieId),
                        dao,
                        cache,
                        "TAG");
        presenter.bindView(view);
        return presenter;
    }

    @Test
    public void testFavoriteClickEnable() throws Exception {
        MovieDetailsContract.DetailsPresenter presenter = getPresenter(MOVIE_ID_1);

        // Check that the movie is not marked as favorite
        // We need to call checkFavorite first so that the "favorite" state is
        // set in the presenter
        presenter.checkFavorite(MOVIE_ID_1);
        verify(view).toggleFavorite(false);

        presenter.onFavoriteClick(MOVIE_ID_1, MOVIE_POSTER_PATH_1);
        verify(view).toggleFavorite(true);

        // Using the DAO, check that the movie is now marked as favorite
        TestSubscriber<FavoriteMovie> ts1 = new TestSubscriber<>();
        dao.get(MOVIE_ID_1).subscribe(ts1);
        ts1.assertNoErrors();
        ts1.assertValueCount(1);
        List<FavoriteMovie> onNextEvents = ts1.getOnNextEvents();

        assertNotNull(onNextEvents.get(0));
        assertThat(onNextEvents.get(0).getMovieId()).isEqualTo(MOVIE_ID_1);

        verifyNoMoreInteractions(view);
    }


    @Test
    public void testFavoriteClickDisable() throws Exception {
        MovieDetailsContract.DetailsPresenter presenter = getPresenter(MOVIE_ID_1);

        FavoriteMovie fm = new FavoriteMovie();
        fm.setMovieId(MOVIE_ID_1);
        fm.setPosterPath(MOVIE_POSTER_PATH_1);
        boolean putResult = dao.put(fm);
        assertThat(putResult).isTrue();


        // We need to call checkFavorite first so that the "favorite" state is
        // set in the presenter
        presenter.checkFavorite(MOVIE_ID_1);
        verify(view).toggleFavorite(true);
        presenter.onFavoriteClick(MOVIE_ID_1, MOVIE_POSTER_PATH_1);
        verify(view).toggleFavorite(false);

        // Using the DAO, check that the movie is now NOT marked as favorite
        TestSubscriber<FavoriteMovie> ts1 = new TestSubscriber<>();
        dao.get(MOVIE_ID_1).subscribe(ts1);
        ts1.assertNoErrors();
        ts1.assertValueCount(1);
        List<FavoriteMovie> onNextEvents = ts1.getOnNextEvents();

        assertNull(onNextEvents.get(0));

        verifyNoMoreInteractions(view);
    }
}
