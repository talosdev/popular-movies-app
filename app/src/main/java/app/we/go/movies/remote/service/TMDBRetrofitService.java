package app.we.go.movies.remote.service;

import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.VideoList;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Note that the {@link Observable}s returned by this method are the ones constructed by
 * retrofit, unmodified, so will be executed synchronously by default.
 *
 * Created by apapad on 8/03/16.
 */
public interface TMDBRetrofitService {


    @GET("movie/{id}")
    Observable<Response<Movie>> getDetails(@Path("id") long movieId);


    @GET("movie/{id}/videos")
    Observable<Response<VideoList>> getVideos(@Path("id") long movieId);


    @GET("movie/{id}/reviews")
    Observable<Response<ReviewList>> getReviews(@Path("id") long movieId);

    @GET("discover/movie")
    Observable<Response<MovieList>> getMovies(@Query("sort_by") String sortBy,
                              @Query("page") int page,
                              @Query("vote_counter.gte") int minVotes);

}
