package app.we.go.movies.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.we.go.movies.remote.json.MovieReviewsJSONParser;
import app.we.go.movies.remote.json.ReviewList;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that encapsulates the code for fetching the details for a movie from TMDB API
 * Created by apapad on 26/11/15.
 */
@Singleton
public class MovieReviewsFetcher extends JSONFetcher {
    private MovieReviewsJSONParser parser = new MovieReviewsJSONParser();
    private TMDBService service;

    @Inject
    public MovieReviewsFetcher(TMDBService service) {
        this.service = service;
    }

    public ReviewList fetch(long id) throws IOException {
       Call<ReviewList> call = service.getReviews(id);

        Response<ReviewList> response = call.execute();

        return response.body();
    }

}
