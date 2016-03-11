package app.we.go.movies.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieJSONParser;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that encapsulates the code for fetching the reviews for a movie from TMDB API
 * Created by apapad on 01/03/2016
 */
@Singleton
public class MovieDetailsFetcher extends JSONFetcher {
    private final TMDBService service;
    private MovieJSONParser parser = new MovieJSONParser();

    @Inject
    public MovieDetailsFetcher(TMDBService service) {
        this.service = service;
    }

    public Movie fetch(long id) throws IOException {

        Call<Movie> call = service.getDetails(id);

        Response<Movie> response = call.execute();

        return response.body();
    }

}
