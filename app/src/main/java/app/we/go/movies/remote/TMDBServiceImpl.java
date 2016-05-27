package app.we.go.movies.remote;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.VideoList;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * TODO I don't like the fact that I am using .observeOn(AndroidSchedulers.mainThread()) here.
 * Maybe adapt the solutions described here?
 * http://appfoundry.be/blog/2015/09/14/mvprx/
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceImpl implements TMDBService {

    // TODO, where to put this? inject by constructor?
    public static final int MINIMUM_VOTES = 500;


    private TMDBRetrofitService retrofitService;

    private TMDBErrorParser errorParser;

    public TMDBServiceImpl(TMDBRetrofitService retrofitService,
                           TMDBErrorParser errorParser) {
        this.retrofitService = retrofitService;
        this.errorParser = errorParser;
    }

    @Override
    public Observable<Response<Movie>> getDetails(long movieId) {
        return retrofitService.getDetails(movieId).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<VideoList>> getVideos(long movieId) {
        return retrofitService.getVideos(movieId).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<ReviewList>> getReviews(long movieId) {
        return retrofitService.getReviews(movieId).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<MovieList>> getMovies(SortByCriterion sortBy, int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("TMDB API defines that page should be greater than 0");
        }
        return retrofitService.getMovies(
                convertSortByCriterionToStringParameter(sortBy),
                page,
                MINIMUM_VOTES).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public TMDBError parse(ResponseBody responseBody) {
        return errorParser.parse(responseBody);
    }


    /**
     * Convert the enum value to the parameter value that the API expects
     *
     * @param sortBy
     * @return
     */
    private static String convertSortByCriterionToStringParameter(SortByCriterion sortBy) {
        switch (sortBy) {
            case POPULARITY:
                return "popularity.desc";
            case VOTE:
                return "vote_average.desc";
            default:
                return "";
        }
    }
}
