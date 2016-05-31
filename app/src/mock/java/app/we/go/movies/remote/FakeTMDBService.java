package app.we.go.movies.remote;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */

import com.google.gson.Gson;

import java.io.IOException;

import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.model.remote.VideoList;
import app.we.go.movies.remote.service.TMDBService;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import rx.Observable;
import rx.Observable.Transformer;


/**
 * Fake {@link TMDBService} implementation.
 * <p>
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeTMDBService implements TMDBService {

    private final ResponseBody errorBody;

    private final BehaviorDelegate<TMDBService> delegate;
    private Transformer transformer;

    public FakeTMDBService(BehaviorDelegate<TMDBService> delegate,
                           Transformer<Response<?>, Response<?>> transformer) {
        this.delegate = delegate;
        this.transformer = transformer;


        // Using the Dagger module to get the same Gson instance as in production code
        // This is not absolutely necessary though...
        ApplicationModule module = new ApplicationModule();
        Gson gson = module.provideGson();

        TMDBError tmdbError = new TMDBError();
        tmdbError.setStatusCode(34);
        tmdbError.setStatusMessage("Resource not found");

        errorBody = ResponseBody.create(MediaType.parse("application/json"),
                gson.toJson(tmdbError));
    }


    @Override
    public Observable<Response<Movie>> getDetails(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.
                    returningResponse(DummyData.DUMMY_MOVIE).
                    getDetails(movieId).
                    compose(transformer);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            // returningResponse always returns a successful response, so we need to use
            // returning here
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getDetails(movieId).
                    compose(transformer);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Observable<Response<VideoList>> getVideos(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.
                    returningResponse(DummyData.VIDEOS).
                    getVideos(movieId).
                    compose(transformer);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getVideos(movieId).
                    compose(transformer);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);


    }

    @Override
    public Observable<Response<ReviewList>> getReviews(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.
                    returningResponse(DummyData.REVIEWS).
                    getReviews(movieId)
                    .compose(transformer);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getReviews(movieId).
                    compose(transformer);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Observable<Response<MovieList>> getMovies(SortByCriterion sortBy, int page) {
        switch (sortBy) {
            case POPULARITY:
                if (page == 1) {
                    return delegate.
                            returningResponse(DummyData.MOVIE_LIST_POPULAR_1).
                            getMovies(sortBy, page).
                            compose(transformer);
                } else if (page == 2) {
                    return delegate.
                            returningResponse(DummyData.MOVIE_LIST_POPULAR_2).
                            getMovies(sortBy, page).
                            compose(transformer);
                }
            case VOTE:
                return delegate.
                        returningResponse(DummyData.MOVIE_LIST_VOTES).
                        getMovies(sortBy, page).
                        compose(transformer);
            case FAVORITES:
                return delegate.
                        returningResponse(DummyData.MOVIE_LIST_FAVORITES).
                        getMovies(sortBy, page).
                        compose(transformer);
        }


        return null;
    }

    @Override
    public TMDBError parse(ResponseBody responseBody) {
        return new TMDBError();
    }


}

