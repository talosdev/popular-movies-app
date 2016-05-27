package app.we.go.movies.movielist;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.util.RxUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

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
        Observable<MovieList> movieListObservable = service.getMovies(sortBy, currentPage);

        subscription = movieListObservable.
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<MovieList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        onFail("Error receiving movie list from server",
                                R.string.error_network,
                                t);
                    }

                    @Override
                    public void onNext(MovieList movieList) {
                        if (movieList != null) {
                            currentPage++;
                            if (movieList.getMovies() != null) {
                                cachedMovies.addAll(movieList.getMovies());
                            }
                            if (getBoundView() != null) {
                                getBoundView().showMovieList(movieList.getMovies());
                            }
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
