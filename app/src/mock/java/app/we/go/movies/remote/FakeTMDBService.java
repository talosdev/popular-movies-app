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
import app.we.go.movies.remote.service.API;
import app.we.go.movies.remote.service.TMDBRetrofitService;
import app.we.go.movies.remote.service.TMDBService;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import rx.Observable;


/**
 * Fake {@link TMDBService} implementation.
 * <p>
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeTMDBService implements TMDBRetrofitService {

    private final ResponseBody errorBody;

    private final BehaviorDelegate<TMDBService> delegate;

    public FakeTMDBService(BehaviorDelegate<TMDBService> delegate) {
        this.delegate = delegate;


        // Using the Dagger module to check the same Gson instance as in production code
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
        if (movieId == DummyData.MOVIE_ID_1) {
            return delegate.
                    returningResponse(DummyData.DUMMY_MOVIE_1).
                    getDetails(movieId);
        } else if (movieId == DummyData.MOVIE_ID_2) {
            return delegate.
                    returningResponse(DummyData.DUMMY_MOVIE_2).
                    getDetails(movieId);
        }  else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            // returningResponse always returns a successful response, so we need to use
            // returning here
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getDetails(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Observable<Response<VideoList>> getVideos(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID_1) {
            return delegate.
                    returningResponse(DummyData.VIDEOS).
                    getVideos(movieId);
        } else if (movieId == DummyData.MOVIE_ID_2) {
            return delegate.
                    returningResponse(DummyData.VIDEOS).
                    getVideos(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getVideos(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);


    }

    @Override
    public Observable<Response<ReviewList>> getReviews(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID_1) {
            return delegate.
                    returningResponse(DummyData.REVIEWS).
                    getReviews(movieId);
        } else if (movieId == DummyData.MOVIE_ID_2) {
            return delegate.
                    returningResponse(DummyData.REVIEWS).
                    getReviews(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.
                    returning(Calls.response(Response.error(404, errorBody))).
                    getReviews(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }


    @Override
    public Observable<Response<MovieList>> getMovies(@Query("sort_by") String sortBy, @Query("page") int page, @Query("vote_count.gte") int minVotes) {
       SortByCriterion sortByCriterion = API.stringToSortByCriterion(sortBy);

        switch (sortByCriterion) {
            case POPULARITY:
                if (page == 1) {
                    return delegate.
                            returningResponse(DummyData.MOVIE_LIST_POPULAR_1).
                            getMovies(sortByCriterion, page);
                } else if (page == 2) {
                    return delegate.
                            returningResponse(DummyData.MOVIE_LIST_POPULAR_2).
                            getMovies(sortByCriterion, page);
                }
            case VOTE:
                return delegate.
                        returningResponse(DummyData.MOVIE_LIST_VOTES).
                        getMovies(sortByCriterion, page);
            case FAVORITES:
                return delegate.
                        returningResponse(DummyData.MOVIE_LIST_FAVORITES).
                        getMovies(sortByCriterion, page);
        }

        return null;
    }
}

