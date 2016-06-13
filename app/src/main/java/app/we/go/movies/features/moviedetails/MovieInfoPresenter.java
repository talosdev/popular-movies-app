package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.movies.R;
import app.we.go.movies.helpers.SharedPreferencesHelper;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.framework.util.RxUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieInfoPresenter extends BaseCacheablePresenter<MovieDetailsContract.InfoView>
        implements MovieDetailsContract.MovieInfoPresenter {

    private final Observable<Response<Movie>> observable;
    private final SharedPreferencesHelper sharedPrefsHelper;
    private final TMDBService service;

    private Subscription subscription;

    private Movie movie;

    public MovieInfoPresenter(TMDBService service,
                              Observable<Response<Movie>> observable,
                              SharedPreferencesHelper sharedPrefsHelper,
                              PresenterCache cache,
                              String tag) {
        super(cache, tag);
        this.service = service;
        this.observable = observable;
        this.sharedPrefsHelper = sharedPrefsHelper;
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
                                    TMDBError error = service.parse(response.errorBody());
                                    onCallError("The call to get the movie details was not successful",
                                            R.string.error_generic, error);
                                }
                            }
                        }

                );


    }

    private void populateViews(Movie movie) {
        if (movie != null) {
            getBoundView().displayInfo(movie);
            getBoundView().displayFormattedDate(sharedPrefsHelper.formatDate(movie.getReleaseDate()));

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

    public static class Factory implements PresenterFactory<MovieInfoPresenter> {

        private TMDBService service;
        private Observable<Response<Movie>> observable;
        private SharedPreferencesHelper sharedPrefsHelper;
        private PresenterCache cache;

        public Factory(TMDBService service,
                       Observable<Response<Movie>> observable,
                       SharedPreferencesHelper sharedPrefsHelper,
                       PresenterCache cache) {

            this.service = service;
            this.observable = observable;
            this.sharedPrefsHelper = sharedPrefsHelper;
            this.cache = cache;
        }


        @NonNull
        @Override
        public MovieInfoPresenter createPresenter(String tag) {
            return new MovieInfoPresenter(service, observable,
                    sharedPrefsHelper,
                    cache,
                    tag);
        }
    }
}
