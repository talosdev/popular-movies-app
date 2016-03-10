package app.we.go.movies.remote;

import java.io.IOException;

import app.we.go.movies.BuildConfig;
import app.we.go.movies.remote.json.MovieReviewsJSONParser;
import app.we.go.movies.remote.json.ReviewList;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that encapsulates the code for fetching the details for a movie from TMDB API
 * Created by apapad on 26/11/15.
 */
public class MovieReviewsFetcher extends JSONFetcher {
    private MovieReviewsJSONParser parser = new MovieReviewsJSONParser();
    private TMDBService service;

    public MovieReviewsFetcher(TMDBService service) {
        this.service = service;
    }

    public ReviewList fetch(long id) throws IOException {
       Call<ReviewList> call = service.getReviews(id, BuildConfig.TMDB_API_KEY);

        Response<ReviewList> response = call.execute();

        return response.body();
    }

}
