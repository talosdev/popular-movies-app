package app.we.go.movies.features.moviedetails.dependency;

import android.content.Context;

import javax.inject.Named;

import app.we.go.movies.dependency.FragmentScope;
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
public class DetailsServiceModule {


    private Context context;
    private final long movieId;


    public DetailsServiceModule(Context context, long movieId) {
        this.context = context;
        this.movieId = movieId;
    }


    @Provides
    @FragmentScope
    public Context provideContext() {
        return context;
    }


    /**
     * This, at the moment is not used, as all views actually have a copy of the movieId.
     *
     * @return
     */
    @Provides
    @FragmentScope
    @Named("movieId")
    public long provideMovieId() {
        return movieId;
    }


    @Provides
    @FragmentScope
    public Observable<Response<Movie>> provideObservable(TMDBService service,
                                                         @Named("movieId") long movieId) {
        return service.getDetails(movieId);
    }


}