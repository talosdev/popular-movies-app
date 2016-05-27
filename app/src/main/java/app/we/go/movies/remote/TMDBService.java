package app.we.go.movies.remote;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieList;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.VideoList;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by apapad on 8/03/16.
 */
public interface TMDBService {


    Observable<Movie> getDetails(long movieId);


    Observable<VideoList> getVideos(long movieId);


    Observable<ReviewList> getReviews(long movieId);

    /**
     *
     * @param sortBy
     * @param page 1-based
     * @return
     */
    Observable<MovieList> getMovies(SortByCriterion sortBy, int page);


    TMDBError parse(ResponseBody responseBody);
}
