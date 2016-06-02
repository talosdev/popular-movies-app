package app.we.go.movies.features.moviedetails.dependency;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.dependency.ScreenScope;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.MovieDetailsPresenter;
import app.we.go.movies.helpers.SharedPreferencesHelper;
import app.we.go.movies.mvp.PresenterCache;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieDetailsModule {


    private final Activity activity;
    private final long movieId;
    private String presenterTag;


    public MovieDetailsModule(Activity activity, long movieId, String presenterTag) {
        this.activity = activity;
        this.movieId = movieId;
        this.presenterTag = presenterTag;
    }

    @Provides
    @ScreenScope
    public Context provideContext() {
        return activity;
    }


    /**
     * This, at the moment is not used, as all views actually have a copy of the movieId.
     * @return
     */
    @Provides
    @ScreenScope
    @Named("movieId")
    public long provideMovieId() {
        return movieId;
    }




    @Provides
    @ScreenScope
    public MovieDetailsContract.Presenter providePresenter(TMDBService service,
                                                           SharedPreferencesHelper sharedPrefsHelper,
                                                           FavoriteMovieDAO favoriteMovieDAO,
                                                           PresenterCache cache) {
        return new MovieDetailsPresenter(service,
                sharedPrefsHelper,
                favoriteMovieDAO,
                cache,
                presenterTag);
    }




}


