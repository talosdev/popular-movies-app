package app.we.go.movies.remote;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.VideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by apapad on 8/03/16.
 */
public interface TMDBService {


    @GET("movie/{id}")
    Call<Movie> getDetails(@Path("id") long movieId, @Query(TMDB.PARAM_API_KEY) String apiKey);


    @GET("movie/{id}/videos")
    Call<VideoList> getVideos(@Path("id") long movieId, @Query(TMDB.PARAM_API_KEY) String apiKey);


    @GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(@Path("id") long movieId, @Query(TMDB.PARAM_API_KEY) String apiKey);



}
