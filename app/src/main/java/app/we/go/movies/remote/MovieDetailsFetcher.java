package app.we.go.movies.remote;

import java.io.IOException;

import app.we.go.movies.BuildConfig;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieJSONParser;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that encapsulates the code for fetching the reviews for a movie from TMDB API
 * Created by apapad on 01/03/2016
 */
public class MovieDetailsFetcher extends JSONFetcher {
    private final TMDBService service;
    private MovieJSONParser parser = new MovieJSONParser();

    public MovieDetailsFetcher(TMDBService service) {
        this.service = service;
    }

    public Movie fetch(long id) throws IOException {

        Call<Movie> call = service.getDetails(id, BuildConfig.TMDB_API_KEY);

        Response<Movie> response = call.execute();

        return response.body();
    }

}
