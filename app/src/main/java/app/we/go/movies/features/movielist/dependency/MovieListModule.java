package app.we.go.movies.features.movielist.dependency;

import android.content.Context;

import app.we.go.movies.dependency.ScreenScope;
import app.we.go.movies.features.movielist.MovieListContract;
import app.we.go.movies.features.movielist.MovieListPresenter;
import app.we.go.movies.mvp.PresenterCache;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieListModule {


    private final Context context;
    private String presenterTag;


    public MovieListModule(Context context,
                           String presenterTag) {
        this.context = context;
        this.presenterTag = presenterTag;
    }

    @Provides
    @ScreenScope
    public Context provideContext() {
        return context;
    }



    @Provides
    @ScreenScope
    public MovieListContract.Presenter providePresenter(TMDBService service,
                                                        PresenterCache cache) {
        return new MovieListPresenter(service, cache, presenterTag);
    }


}


