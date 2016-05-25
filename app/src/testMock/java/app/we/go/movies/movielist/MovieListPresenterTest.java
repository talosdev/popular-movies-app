package app.we.go.movies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.MockTMDBServiceSync;
import app.we.go.movies.remote.TMDBService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenterTest {

    @Mock
    MovieListContract.View view;

    MovieListPresenter presenter;

    TMDBService service;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        service = new MockTMDBServiceSync();

        presenter = new MovieListPresenter(service);
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoad() throws Exception {
        presenter.loadMovies(SortByCriterion.POPULARITY);

        verify(view).showMovieList(DummyData.MOVIE_LIST.getMovies());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOpenMovie() throws Exception {
        presenter.openMovieDetails(DummyData.DUMMY_MOVIE);

        verify(view).showMovieDetails(eq(DummyData.DUMMY_MOVIE));
        verifyNoMoreInteractions(view);
    }


}
