package app.we.go.movies.remote;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.VideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by apapad on 8/03/16.
 */
public interface TMDBService {


    @GET("movie/{id}")
    Call<Movie> getDetails(@Path("id") long movieId);


    @GET("movie/{id}/videos")
    Call<VideoList> getVideos(@Path("id") long movieId);


    @GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(@Path("id") long movieId);



}
