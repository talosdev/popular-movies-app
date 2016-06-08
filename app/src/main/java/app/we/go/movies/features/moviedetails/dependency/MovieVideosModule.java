package app.we.go.movies.features.moviedetails.dependency;

import android.content.Context;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieVideosPresenter;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieVideosModule {

    private Context context;
    private String presenterTag;

    public MovieVideosModule(Context context,
                             String presenterTag) {
        this.context = context;
        this.presenterTag = presenterTag;
    }

    @Provides
    @FragmentScope
    public Context provideContext() {
        return context;
    }

    @Provides
    @FragmentScope
    public MovieDetailsContract.VideosPresenter provideVideosPresenter(MovieVideosPresenter.Factory factory,
                                                                       PresenterCache cache) {
        return cache.getPresenter(presenterTag, factory);
    }


    @Provides
    @FragmentScope
    public MovieVideosPresenter.Factory providePresenterFactory(TMDBService service,
                                                                              URLBuilder urlBuilder,
                                                                              PresenterCache cache) {
        return new MovieVideosPresenter.Factory(service,
                urlBuilder,
                cache);
    }


}