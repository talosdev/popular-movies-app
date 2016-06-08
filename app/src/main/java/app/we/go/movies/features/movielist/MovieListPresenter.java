package app.we.go.movies.features.movielist;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.movies.R;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenter extends BaseCacheablePresenter<MovieListContract.View>
        implements MovieListContract.Presenter {

    private final TMDBService service;
    private FavoriteMovieDAO dao;

    private int currentPage = 1;
    private Subscription subscription;

    public MovieListPresenter(TMDBService service,
                              FavoriteMovieDAO dao,
                              PresenterCache cache,
                              String tag) {
        super(cache, tag);
        this.service = service;
        this.dao = dao;
    }

    private final List<Movie> cachedMovies = new ArrayList<>();

    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
    }

    @Override
    public void loadMovies(SortByCriterion sortBy) {

        switch (sortBy) {

            case POPULARITY:
            case VOTE:
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
                                        getBoundView().showMovieList(movieList.getMovies());
                                    }
                                } else {
                                    TMDBError error = service.parse(response.errorBody());
                                    onCallError("The call to get the movie list was not successful",
                                            R.string.error_generic, error);
                                }
                            }
                        });
                break;
            case FAVORITES:
                dao.getAll(new FavoriteMovieDAO.Callback<List<FavoriteMovie>>() {
                    @Override
                    public void onSuccess(List<FavoriteMovie> result) {
                        currentPage++;
                        // TODO: observable transformations???
                        List<Movie> movies = new ArrayList<Movie>();
                        for (FavoriteMovie fm: result) {
                            Movie m = new Movie();
                            m.setId(fm.getMovieId());
                            m.setPosterPath(fm.getPosterPath());
                            movies.add(m);
                        }
                        cachedMovies.addAll(movies);
                        getBoundView().showMovieList(movies);
                    }

                    @Override
                    public void onError() {
                        getBoundView().showError(null, "Database error", R.string.error_database, null);
                    }
                },
                        (currentPage - 1) * TMDB.MOVIES_PER_PAGE,  // offset
                        TMDB.MOVIES_PER_PAGE); // limit
                break;
        }

    }


    @Override
    public void openMovieDetails(Movie movie) {
        if (isViewBound()) {
            getBoundView().navigateToMovieDetails(movie);
        }
    }

    @Override
    public void onRestoreFromCache() {
        if (isViewBound()) {
            getBoundView().showMovieList(cachedMovies);
        }
    }

    public static class Factory implements PresenterFactory<MovieListPresenter> {

        TMDBService service;
        PresenterCache cache;
        private FavoriteMovieDAO dao;

        public Factory(TMDBService service, PresenterCache cache, FavoriteMovieDAO dao) {
            this.service = service;
            this.cache = cache;
            this.dao = dao;
        }

        @NonNull
        @Override
        public MovieListPresenter createPresenter(String tag) {
            return new MovieListPresenter(service, dao, cache, tag);
        }
    }
}
