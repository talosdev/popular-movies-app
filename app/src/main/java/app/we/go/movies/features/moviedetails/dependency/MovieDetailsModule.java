package app.we.go.movies.features.moviedetails.dependency;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import app.we.go.movies.helpers.SharedPreferencesHelper;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.dependency.ActivityScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieDetailsPresenter;
import app.we.go.movies.features.moviedetails.MovieReviewsPresenter;
import app.we.go.movies.features.moviedetails.MovieVideosPresenter;
import app.we.go.movies.remote.service.TMDBService;
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
                                                           SharedPreferencesHelper sharedPrefsHelper,
                                                           FavoriteMovieDAO favoriteMovieDAO) {
        return new MovieDetailsPresenter(service, sharedPrefsHelper, favoriteMovieDAO);
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


