package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.framework.util.RxUtils;
import app.we.go.movies.R;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.remote.service.TMDBService;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenter extends BaseCacheablePresenter<MovieDetailsContract.DetailsView>
        implements MovieDetailsContract.DetailsPresenter {

    private TMDBService service;
    private Observable<Response<Movie>> observable;
    private final RxFavoriteMovieDAO favoriteMovieDAO;

    // DetailsPresenter holds the "favorite" state
    private boolean isFavorite;

    private Subscription subscription;
    private Movie movie;


    public MovieDetailsPresenter(TMDBService service, Observable<Response<Movie>> observable,
                                 RxFavoriteMovieDAO favoriteMovieDAO,
                                 PresenterCache cache,
                                 String tag) {
        super(cache, tag);
        this.service = service;
        this.observable = observable;
        this.favoriteMovieDAO = favoriteMovieDAO;
    }

    @Override
    public void loadMovieInfo(long movieId) {

        subscription = observable.
                subscribe(
                        new Observer<Response<Movie>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable t) {
                                onCallFail("Network error getting the movie details",
                                        R.string.error_network,
                                        t);
                            }

                            @Override
                            public void onNext(Response<Movie> response) {
                                if (response.isSuccessful()) {
                                    movie = response.body();

                                    populateViews(movie);
                                } else {
                                    onCallError("The call to check the movie details was not successful",
                                            R.string.error_generic, service.parse(response.errorBody()));
                                }
                            }
                        }

                );


    }



    private void populateViews(Movie movie) {
        if (movie != null) {
            getBoundView().displayTitle(movie.getTitle());
            getBoundView().displayImage(movie.getBackdropPath());
        }
    }


    @Override
    public void checkFavorite(long movieId) {
        Observable<Boolean> favoriteMovieObservable = favoriteMovieDAO.check(movieId);
        favoriteMovieObservable.
                subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Boolean isEmpty) {
                        isFavorite = isEmpty;
                        getBoundView().toggleFavorite(isFavorite);

                    }
                });

    }

    /**
     * Assumes that {@link #checkFavorite(long)} has been called before, so that the
     * "favorite" status is correctly reflected in the field's {@link #isFavorite} value.
     * @param movieId
     * @param posterPath
     */
    @Override
    public void onFavoriteClick(long movieId, String posterPath) {
        if (isFavorite) {
            favoriteMovieDAO.delete(movieId);
        } else {
            favoriteMovieDAO.put(new FavoriteMovie(movieId, posterPath));
        }

        isFavorite = !isFavorite;

        // reflect the change in the ui
        if (isViewBound()) {
            getBoundView().toggleFavorite(isFavorite);
        }
    }


    @Override
    public void onRestoreFromCache() {
        populateViews(movie);
    }


    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
    }

    public static class Factory implements PresenterFactory<MovieDetailsPresenter> {

        private TMDBService service;
        private Observable<Response<Movie>> observable;
        private RxFavoriteMovieDAO favoriteMovieDAO;
        private PresenterCache cache;

        public Factory(TMDBService service, Observable<Response<Movie>> observable,
                       RxFavoriteMovieDAO favoriteMovieDAO,
                       PresenterCache cache) {
            this.service = service;

            this.observable = observable;
            this.favoriteMovieDAO = favoriteMovieDAO;
            this.cache = cache;
        }


        @NonNull
        @Override
        public MovieDetailsPresenter createPresenter(String tag) {
            return new MovieDetailsPresenter(service, observable,
                    favoriteMovieDAO,
                    cache,
                    tag);
        }
    }
}
