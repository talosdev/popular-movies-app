package app.we.go.movies.features.movielist.dependency;

import android.app.Activity;
import android.content.Context;

import app.we.go.movies.dependency.ActivityScope;
import app.we.go.movies.features.movielist.MovieListContract;
import app.we.go.movies.features.movielist.MovieListPresenter;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieListModule {


    private final Activity activity;


    public MovieListModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Context provideContext() {
        return activity;
    }



    @Provides
    @ActivityScope
    public MovieListContract.Presenter providePresenter(TMDBService service) {
        return new MovieListPresenter(service);
    }


}


