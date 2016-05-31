package app.we.go.movies.remote.service;

import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.model.remote.VideoList;
import app.we.go.movies.remote.TMDBErrorParser;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBNonRxServiceImpl implements TMDBNonRxService {

    // TODO, where to put this? inject by constructor?
    public static final int MINIMUM_VOTES = 500;


    private final TMDBRetrofitNonRxService retrofitService;

    private final TMDBErrorParser errorParser;

    public TMDBNonRxServiceImpl(TMDBRetrofitNonRxService retrofitService,
                                TMDBErrorParser errorParser) {
        this.retrofitService = retrofitService;
        this.errorParser = errorParser;
    }

    @Override
    public Call<Movie> getDetails(long movieId) {
        return retrofitService.getDetails(movieId);
    }

    @Override
    public Call<VideoList> getVideos(long movieId) {
        return retrofitService.getVideos(movieId);
    }

    @Override
    public Call<ReviewList> getReviews(long movieId) {
        return retrofitService.getReviews(movieId);
    }

    @Override
    public Call<MovieList> getMovies(SortByCriterion sortBy, int page) {
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
