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
import rx.Observable.Transformer;

/**
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceImpl implements TMDBService {

    // TODO, where to put this? inject by constructor?
    public static final int MINIMUM_VOTES = 500;


    private TMDBRetrofitService retrofitService;

    private TMDBErrorParser errorParser;
    private Transformer transformer;

    public TMDBServiceImpl(TMDBRetrofitService retrofitService,
                           TMDBErrorParser errorParser,
                           Transformer<Response<?>, Response<?>>  transformer) {
        this.retrofitService = retrofitService;
        this.errorParser = errorParser;
        this.transformer = transformer;
    }

    @Override
    public Observable<Response<Movie>> getDetails(long movieId) {
        return retrofitService.getDetails(movieId).compose(transformer);
    }

    @Override
    public Observable<Response<VideoList>> getVideos(long movieId) {
        return retrofitService.getVideos(movieId).compose(transformer);
    }

    @Override
    public Observable<Response<ReviewList>> getReviews(long movieId) {
        return retrofitService.getReviews(movieId).compose(transformer);
    }

    @Override
    public Observable<Response<MovieList>> getMovies(SortByCriterion sortBy, int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("TMDB API defines that page should be greater than 0");
        }
        return retrofitService.getMovies(
                convertSortByCriterionToStringParameter(sortBy),
                page,
                MINIMUM_VOTES).compose(transformer);
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
