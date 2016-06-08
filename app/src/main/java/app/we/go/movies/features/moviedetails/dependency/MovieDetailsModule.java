package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieDetailsPresenter;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieDetailsModule {


    private String presenterTag;

    public MovieDetailsModule(String presenterTag) {
        this.presenterTag = presenterTag;
    }

    @Provides
    @FragmentScope
    public MovieDetailsContract.DetailsPresenter provideDetailsPresenter(PresenterCache cache,
                                                                         MovieDetailsPresenter.Factory factory) {
        return cache.getPresenter(presenterTag, factory);
    }

    @Provides
    @FragmentScope
    public MovieDetailsPresenter.Factory provideDetailsPresenterFactory(
            TMDBService service,
            Observable<Response<Movie>> observable,
            FavoriteMovieDAO favoriteMovieDAO,
            PresenterCache cache) {
        return new MovieDetailsPresenter.Factory(service, observable, favoriteMovieDAO, cache);
    }



}


