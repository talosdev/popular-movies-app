package app.we.go.movies.features.movielist;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.movies.R;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.model.db.FavoriteMovie;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.util.LOG;
import app.we.go.movies.util.RxUtils;
import nl.nl2312.rxcupboard.OnDatabaseChange;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenter extends BaseCacheablePresenter<MovieListContract.View>
        implements MovieListContract.Presenter {

    private final TMDBService service;
    private RxFavoriteMovieDAO dao;
    private SortByCriterion sortBy;

    private int currentPage = 1;
    private CompositeSubscription composite;

    private final List<Movie> cachedMovies = new ArrayList<>();
    private Subscription changesSubscription;

    public MovieListPresenter(TMDBService service,
                              RxFavoriteMovieDAO dao,
                              SortByCriterion sortBy,
                              PresenterCache cache,
                              String tag) {
        super(cache, tag);
        this.service = service;
        this.dao = dao;
        this.sortBy = sortBy;
        composite = new CompositeSubscription();


    }


    @Override
    public void bindView(MovieListContract.View view) {
        super.bindView(view);
        if (sortBy == SortByCriterion.FAVORITES) {
            changesSubscription = dao.getChangesObservable().subscribe(
                    new OnDatabaseChange<FavoriteMovie>() {
                        @Override
                        public void onInsert(FavoriteMovie entity) {
                            LOG.d(Tags.DB, "OnInsert handle %d", entity.getMovieId());
                            boolean contains = false;
                            for (Movie m : cachedMovies) {
                                if (m.getId() == entity.getMovieId()) {
                                    contains = true;
                                    break;
                                }
                            }
                            if (!contains) {
                                Movie newMovie = new Movie();
                                newMovie.setId(entity.getMovieId());
                                newMovie.setPosterPath(entity.getPosterPath());
                                cachedMovies.add(newMovie);
                            }
                        }

                        @Override
                        public void onDelete(FavoriteMovie entity) {
                            LOG.d(Tags.DB, "onDelete handle %d", entity.getMovieId());
                            for (Movie m : cachedMovies) {
                                if (m.getId() == entity.getMovieId()) {
                                    LOG.d(Tags.DB, "Removing from presenter's cached movies");
                                    cachedMovies.remove(m);
                                    break;
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(composite);
    }

    @Override
    public void loadMovies() {

        switch (sortBy) {

            case POPULARITY:
            case VOTE:
                Subscription serviceSubscription = service.getMovies(sortBy, currentPage).
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

                composite.add(serviceSubscription);
                break;
            case FAVORITES:

                Observable<List<FavoriteMovie>> favoriteMovieObservable = dao.get((currentPage - 1) * TMDB.MOVIES_PER_PAGE,  // offset
                        TMDB.MOVIES_PER_PAGE);// limit)

                Observable<List<Movie>> moviesObservable = favoriteMovieObservable.flatMapIterable(new Func1<List<FavoriteMovie>, Iterable<FavoriteMovie>>() {
                    @Override
                    public Iterable<FavoriteMovie> call(List<FavoriteMovie> favoriteMovies) {
                        return favoriteMovies;
                    }
                }).map(new Func1<FavoriteMovie, Movie>() {

                    @Override
                    public Movie call(FavoriteMovie favoriteMovie) {
                        Movie m = new Movie();
                        m.setId(favoriteMovie.getMovieId());
                        m.setPosterPath(favoriteMovie.getPosterPath());
                        return m;
                    }
                }).toList();


                moviesObservable.subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        currentPage++;
                        cachedMovies.addAll(movies);
                        getBoundView().showMovieList(movies);
                    }
                });

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


    @Override
    public void clear() {
        super.clear();
        if (changesSubscription != null) {
            changesSubscription.unsubscribe();
        }
    }

    public static class Factory implements PresenterFactory<MovieListPresenter> {

        TMDBService service;
        PresenterCache cache;
        private RxFavoriteMovieDAO dao;
        private SortByCriterion sortBy;

        public Factory(TMDBService service, PresenterCache cache,
                       RxFavoriteMovieDAO dao, SortByCriterion sortBy) {
            this.service = service;
            this.cache = cache;
            this.dao = dao;
            this.sortBy = sortBy;
        }

        @NonNull
        @Override
        public MovieListPresenter createPresenter(String tag) {
            return new MovieListPresenter(service, dao, sortBy, cache, tag);
        }
    }
}
