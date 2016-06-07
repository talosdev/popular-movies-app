package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieInfoPresenter;
import app.we.go.movies.helpers.SharedPreferencesHelper;
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
public class MovieInfoModule {

    private String presenterTag;

    public MovieInfoModule(String presenterTag) {;
        this.presenterTag = presenterTag;
    }


    @Provides
    @FragmentScope
    public MovieDetailsContract.MovieInfoPresenter provideInfoPresenter(PresenterCache cache,
                                                                        MovieInfoPresenter.Factory factory) {
        return cache.getPresenter(presenterTag, factory);
    }


    @Provides
    @FragmentScope
    public MovieInfoPresenter.Factory provideInfoPresenterFactory(TMDBService service,
                                                                  Observable<Response<Movie>> observable,
                                                                  SharedPreferencesHelper sharedPrefsHelper,
                                                                  PresenterCache cache) {
        return new MovieInfoPresenter.Factory(service, observable, sharedPrefsHelper, cache);
    }

}