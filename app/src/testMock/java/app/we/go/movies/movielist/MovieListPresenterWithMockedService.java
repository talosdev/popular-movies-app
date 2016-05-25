package app.we.go.movies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenterWithMockedService {

    @Mock
    MovieListContract.View view;

    MovieListPresenter presenter;

    @Mock
    TMDBService service;

    @Captor
    ArgumentCaptor<Callback> captor;



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        presenter = new MovieListPresenter(service);
        presenter.bindView(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test that the presenter correctly maintains the paging state, and that
     * the service in successive calls of the
     * {@link app.we.go.movies.movielist.MovieListContract.Presenter#loadMovies(SortByCriterion)}
     * method, the correct page number is requested.
     *
     * @throws Exception
     */
    @Test
    public void testPaging() throws Exception {


        for (int i=0; i<10; i++) {
            Call<MovieList> call = Mockito.mock(Call.class);
            when(service.getMovies(any(SortByCriterion.class), anyInt())).thenReturn(call);

            presenter.loadMovies(SortByCriterion.POPULARITY);
            verify(call).enqueue(captor.capture());
            captor.getValue().onResponse(call, Response.success(DummyData.MOVIE_LIST));
            verify(service).getMovies(SortByCriterion.POPULARITY, i);
        }

    }


}