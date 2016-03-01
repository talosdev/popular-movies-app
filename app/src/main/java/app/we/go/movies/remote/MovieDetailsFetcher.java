package app.we.go.movies.remote;

import java.io.IOException;
import java.net.URL;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieJSONParser;

/**
 * Class that encapsulates the code for fetching the reviews for a movie from TMDB API
 * Created by apapad on 01/03/2016
 */
public class MovieDetailsFetcher extends JSONFetcher {
    private MovieJSONParser parser = new MovieJSONParser();
    private URLBuilder urlBuilder;

    public MovieDetailsFetcher() {
        urlBuilder = new URLBuilder();

    }

    public Movie fetch(long id) throws IOException {

        URL url = urlBuilder.buildMovieDetailsUrl(id);
        String jsonString = getJSON(url);
        if (jsonString == null) return null;
        return parser.parseMovie(jsonString);
    }

}
