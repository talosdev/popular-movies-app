package app.we.go.movies.features.movielist.dependency;

import android.content.Context;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.movielist.MovieListContract;
import app.we.go.movies.features.movielist.MovieListPresenter;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.remote.service.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MovieListModule {


    private final Context context;
    private final SortByCriterion sortBy;
    private final String presenterTag;



    public MovieListModule(Context context, SortByCriterion sortBy, String presenterTag) {
        this.context = context;
        this.sortBy = sortBy;
        this.presenterTag = presenterTag;
    }

    @Provides
    @FragmentScope
    public Context provideContext() {
        return context;
    }

    @Provides
    @FragmentScope
    public SortByCriterion provideSortByCriterion() {
        return sortBy;
    }


    @Provides
    @FragmentScope
    public MovieListContract.Presenter providePresenter(PresenterCache cache,
                                                        MovieListPresenter.Factory factory) {
        return cache.getPresenter(presenterTag, factory);
    }

    @Provides
    @FragmentScope
    public MovieListPresenter.Factory provideFactory(TMDBService service,
                                                     PresenterCache cache,
                                                     RxFavoriteMovieDAO dao, SortByCriterion sortBy)  {


        return new MovieListPresenter.Factory(service, cache,
                dao, sortBy);
    }


}


