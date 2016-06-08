package app.we.go.movies.features.moviedetails.dependency;

import android.content.Context;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieReviewsPresenter;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieReviewsModule {

    private Context context;
    private String presenterTag;

    public MovieReviewsModule(Context context, String presenterTag) {
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
    public MovieDetailsContract.ReviewsPresenter provideReviewsPresenter(PresenterCache cache,
            MovieReviewsPresenter.Factory factory) {
        return cache.getPresenter(presenterTag, factory);
    }

    @Provides
    @FragmentScope
    public MovieReviewsPresenter.Factory providePresenterFactory(PresenterCache cache,
                                                                  TMDBService service) {
        return new MovieReviewsPresenter.Factory(cache, service);
    }

}