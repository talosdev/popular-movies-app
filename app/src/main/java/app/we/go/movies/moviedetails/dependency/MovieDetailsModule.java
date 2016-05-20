package app.we.go.movies.moviedetails.dependency;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.framework.ActivityScope;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import app.we.go.movies.moviedetails.MovieDetailsPresenter;
import app.we.go.movies.moviedetails.MovieReviewsPresenter;
import app.we.go.movies.moviedetails.MovieVideosPresenter;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieDetailsModule {


    private Activity activity;
    private long movieId;


    public MovieDetailsModule(Activity activity, long movieId) {
        this.activity = activity;
        this.movieId = movieId;
    }


    @Provides
    @ActivityScope
    public Context provideContext() {
        return activity;
    }


    /**
     * This, at the moment is not used, as all views actually have a copy of the movieId.
     * @return
     */
    @Provides
    @ActivityScope
    @Named("movieId")
    public long provideMovieId() {
        return movieId;
    }




    @Provides
    @ActivityScope
    public MovieDetailsContract.Presenter providePresenter(TMDBService service,
                                                           SharedPreferencesHelper sharedPrefsHelper) {
        return new MovieDetailsPresenter(service, sharedPrefsHelper);
    }


    @Provides
    @ActivityScope
    public MovieDetailsContract.ReviewsPresenter provideReviewsPresenter(TMDBService service) {
        return new MovieReviewsPresenter(service);
    }

    @Provides
    @ActivityScope
    public MovieDetailsContract.VideosPresenter provideVideosPresenter(TMDBService service,
                                                                       URLBuilder urlBuilder) {
        return new MovieVideosPresenter(service, urlBuilder);
    }
}


