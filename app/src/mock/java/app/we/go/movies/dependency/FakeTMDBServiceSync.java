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
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.Calls;


/**
 * Fake {@link TMDBService} implementation. We don't use retrofit-mock's
 * {@link retrofit2.mock.BehaviorDelegate} here, since that would make the service
 * to operate as an async service.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeTMDBServiceSync implements TMDBService {

    private final ResponseBody errorBody;

    public FakeTMDBServiceSync() {
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
            return Calls.response(DummyData.DUMMY_MOVIE);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {

            return Calls.response(Response.<Movie>error(404, errorBody));

        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Calls.failure(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);
    }

    @Override
    public Call<VideoList> getVideos(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return Calls.response(DummyData.VIDEOS);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return Calls.response(Response.<VideoList>error(404, errorBody));
        } else if (movieId == DummyData.MOVIE_ID_CAUSES_SERVER_ERROR) {
            return Calls.failure(new IOException("Error contacting server"));
        }
        throw new IllegalArgumentException("Method is not prepared to accept input value " + movieId);


    }

    @Override
    public Call<ReviewList> getReviews(@Path("id") long movieId) {
        if (movieId == DummyData.MOVIE_ID) {
            return Calls.response(DummyData.REVIEWS);
        } else if (movieId == DummyData.INEXISTENT_MOVIE_ID) {
            return Calls.response(Response.<ReviewList>error(404, errorBody));
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
                    return Calls.response(DummyData.MOVIE_LIST_POPULAR_1);
                } else if (page == 2) {
                    return Calls.response(DummyData.MOVIE_LIST_POPULAR_2);
                }
            case VOTE:
                return Calls.response(DummyData.MOVIE_LIST_VOTES);
            case FAVORITES:
                return Calls.response(DummyData.MOVIE_LIST_FAVORITES);
            default:
                return null;
        }


    }
    @Override
    public TMDBError parse(ResponseBody responseBody) {
        return new TMDBError();
    }
}
