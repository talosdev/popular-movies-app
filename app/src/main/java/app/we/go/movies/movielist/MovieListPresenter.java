package app.we.go.movies.movielist;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenter extends AbstractPresenter<MovieListContract.View>
        implements MovieListContract.Presenter {

    private TMDBService service;

    private int currentPage = 1;
    private Subscription subscription;

    public MovieListPresenter(TMDBService service) {
        this.service = service;
    }

    private List<Movie> cachedMovies = new ArrayList<>();

    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
    }

    @Override
    public void loadMovies(SortByCriterion sortBy) {

        subscription = service.getMovies(sortBy, currentPage).
                subscribe(new Observer<Response<MovieList>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        onCallFail("Error receiving movie list from server",
                                R.string.error_network,
                                t);
                    }

                    @Override
                    public void onNext(Response<MovieList> response) {
                        if (response.isSuccessful()) {
                            MovieList movieList = response.body();

                            if (movieList != null) {
                                currentPage++;
                                if (movieList.getMovies() != null) {
                                    cachedMovies.addAll(movieList.getMovies());
                                }
                                if (getBoundView() != null) {
                                    getBoundView().showMovieList(movieList.getMovies());
                                }
                            }
                        } else {
                            TMDBError error = service.parse(response.errorBody());
                            onCallError("The call to get the movie list was not successful",
                                    R.string.error_generic, error);
                        }
                    }
                });
    }


    @Override
    public void openMovieDetails(Movie movie) {
        if (getBoundView() != null) {
            getBoundView().navigateToMovieDetails(movie);
        }
    }
}
