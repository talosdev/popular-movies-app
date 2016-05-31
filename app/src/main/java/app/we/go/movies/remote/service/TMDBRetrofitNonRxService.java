package app.we.go.movies.remote.service;

import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.VideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by apapad on 8/03/16.
 */
public interface TMDBRetrofitNonRxService {


    @GET("movie/{id}")
    Call<Movie> getDetails(@Path("id") long movieId);


    @GET("movie/{id}/videos")
    Call<VideoList> getVideos(@Path("id") long movieId);


    @GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(@Path("id") long movieId);

    @GET("discover/movie")
    Call<MovieList> getMovies(@Query("sort_by") String sortBy,
                              @Query("page") int page,
                              @Query("vote_counter.gte") int minVotes);

}
