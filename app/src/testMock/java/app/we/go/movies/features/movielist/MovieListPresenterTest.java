package app.we.go.movies.features.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.db.RxInMemoryFavoriteMoviesDAO;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.service.TMDBService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenterTest {

    @Mock
    MovieListContract.View view;

    @Mock
    private PresenterCache cache;

    RxFavoriteMovieDAO dao = new RxInMemoryFavoriteMoviesDAO();

    MovieListPresenter presenter;
    TMDBService service;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        service = MockServiceModule.FakeTmdbServiceAsyncFactory.getInstance(true);

        presenter = new MovieListPresenter(service,
                dao,
                SortByCriterion.POPULARITY,
                cache,
                "TAG");
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoad() throws Exception {
        presenter.loadMovies();

        verify(view).showMovieList(DummyData.MOVIE_LIST_POPULAR_1.getMovies());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOpenMovie() throws Exception {
        presenter.openMovieDetails(DummyData.DUMMY_MOVIE);

        verify(view).navigateToMovieDetails(eq(DummyData.DUMMY_MOVIE));
        verifyNoMoreInteractions(view);
    }


}