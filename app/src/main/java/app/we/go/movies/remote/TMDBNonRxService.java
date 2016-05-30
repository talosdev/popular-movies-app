package app.we.go.movies.remote;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.VideoList;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by apapad on 8/03/16.
 */
public interface TMDBNonRxService {


    Call<Movie> getDetails(long movieId);


    Call<VideoList> getVideos(long movieId);


    Call<ReviewList> getReviews(long movieId);

    /**
     *
     * @param sortBy
     * @param page 1-based
     * @return
     */
    Call<MovieList> getMovies(SortByCriterion sortBy, int page);


    TMDBError parse(ResponseBody responseBody);
}
