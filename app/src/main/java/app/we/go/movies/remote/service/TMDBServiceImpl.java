package app.we.go.movies.remote.service;


import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.model.remote.VideoList;
import app.we.go.movies.remote.TMDBErrorParser;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceImpl implements TMDBService {

    // TODO, where to put this? inject by constructor?
    public static final int MINIMUM_VOTES = 500;


    private final TMDBRetrofitService retrofitService;

    private final TMDBErrorParser errorParser;

    public TMDBServiceImpl(TMDBRetrofitService retrofitService,
                           TMDBErrorParser errorParser) {
        this.retrofitService = retrofitService;
        this.errorParser = errorParser;


    }

    @Override
    public Observable<Response<Movie>> getDetails(long movieId) {
        return retrofitService.getDetails(movieId);
    }

    @Override
    public Observable<Response<VideoList>> getVideos(long movieId) {
        return retrofitService.getVideos(movieId);
    }

    @Override
    public Observable<Response<ReviewList>> getReviews(long movieId) {
        return retrofitService.getReviews(movieId);
    }


    @Override
    public Observable<Response<MovieList>> getMovies(SortByCriterion sortBy, int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("TMDB API defines that page should be greater than 0");
        }
        return retrofitService.getMovies(
                convertSortByCriterionToStringParameter(sortBy),
                page,
                MINIMUM_VOTES);
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
