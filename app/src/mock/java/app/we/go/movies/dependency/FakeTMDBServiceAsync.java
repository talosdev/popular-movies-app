package app.we.go.movies.dependency;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */

import android.support.test.espresso.IdlingResource;

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
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;


/**
 * Fake {@link TMDBService} implementation.
 *
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
    public Call<Movie> getDetails(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.returningResponse(DummyData.DUMMY_MOVIE).getDetails(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {

            return delegate.returningResponse(Response.<Movie>error(404, errorBody)).getDetails(movieId);

        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Calls.failure(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Call<VideoList> getVideos(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.returningResponse(DummyData.VIDEOS).getVideos(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.returningResponse(Response.<VideoList>error(404, errorBody)).getVideos(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Calls.failure(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);


    }

    @Override
    public Call<ReviewList> getReviews(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return delegate.returningResponse(DummyData.REVIEWS).getReviews(movieId);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return delegate.returningResponse(Response.<ReviewList>error(404, errorBody)).getReviews(movieId);
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Calls.failure(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Call<MovieList> getMovies(SortByCriterion sortBy, int page) {
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

    class CallDecorator<T> extends Call<T> implements IdlingResource {

        Call<T> realCall;
        private boolean isRunning;
        private ResourceCallback resourceCallback;

        public CallDecorator(Call<T> realCall) {
            this.realCall = realCall;
        }

        @Override
        public Response<T> execute() throws IOException {
            isRunning = true;
            Response<T> execute = realCall.execute();
            isRunning = false;
            resourceCallback.onTransitionToIdle();
            return execute;

        }

        @Override
        public void enqueue(final Callback<T> callback) {
            isRunning = true;
            realCall.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    callback.onResponse(call, response);
                    isRunning = false;
                    resourceCallback.onTransitionToIdle();
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    callback.onFailure(call, t);
                    isRunning = false;
                    resourceCallback.onTransitionToIdle();
                }
            });
        }

        @Override
        public boolean isExecuted() {
            return realCall.isExecuted();
        }

        @Override
        public void cancel() {
            realCall.cancel();
        }

        @Override
        public boolean isCanceled() {
            return realCall.isCanceled();
        }

        @Override
        public Call<T> clone() {
            return realCall.clone();
        }

        @Override
        public Request request() {
            return realCall.request();
        }

        @Override
        public String getName() {
            return "IdlingResource";
        }

        @Override
        public boolean isIdleNow() {
            return !isRunning;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            this.resourceCallback =  callback;
        }
    }
}

