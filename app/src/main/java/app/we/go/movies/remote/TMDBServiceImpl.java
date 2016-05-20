package app.we.go.movies.remote;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.VideoList;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBServiceImpl implements TMDBService {

    private TMDBRetrofitService retrofitService;

    private TMDBErrorParser errorParser;

    public TMDBServiceImpl(TMDBRetrofitService retrofitService, TMDBErrorParser errorParser) {
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
    public TMDBError parse(ResponseBody responseBody) {
        return errorParser.parse(responseBody);
    }
}
