package app.we.go.movies.dependency;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */

import com.google.gson.Gson;

import java.io.IOException;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.VideoList;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import rx.Observable;


/**
 * Fake {@link TMDBService} implementation.
 * <p>
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeTMDBServiceAsync implements TMDBService {

    private final ResponseBody errorBody;

    private final BehaviorDelegate<TMDBService> delegate;

    public FakeTMDBServiceAsync(BehaviorDelegate<TMDBService> delegate) {
        this.delegate = delegate;

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
            return delegate.returningResponse(DummyData.DUMMY_MOVIE).getDetails(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {

            return delegate.returningResponse(Response.<Movie>error(404, errorBody)).getDetails(movieId);

        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Observable<Response<VideoList>> getVideos(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.returningResponse(DummyData.VIDEOS).getVideos(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.returningResponse(Response.<VideoList>error(404, errorBody)).getVideos(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Observable.error(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);


    }

    @Override
    public Observable<Response<ReviewList>> getReviews(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.returningResponse(DummyData.REVIEWS).getReviews(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.returningResponse(Response.<ReviewList>error(404, errorBody)).getReviews(movieId);
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
                    return delegate.returningResponse(DummyData.MOVIE_LIST_POPULAR_1).getMovies(sortBy, page);
                } else if (page == 2) {
                    return delegate.returningResponse(DummyData.MOVIE_LIST_POPULAR_2).getMovies(sortBy, page);
                }
            case VOTE:
                return delegate.returningResponse(DummyData.MOVIE_LIST_VOTES).getMovies(sortBy, page);
            case FAVORITES:
                return delegate.returningResponse(DummyData.MOVIE_LIST_FAVORITES).getMovies(sortBy, page);
            default:
                return null;
        }


    }

    @Override
    public TMDBError parse(ResponseBody responseBody) {
        return new TMDBError();
    }


}

