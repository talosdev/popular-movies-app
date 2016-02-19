package app.we.go.movies.remote;

import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.MovieJSONParser;
import app.we.go.movies.remote.json.MovieList;

import java.io.IOException;
import java.net.URL;

/**
 * Class that encapsulates the code for fetching the list of movies from TMDB API
 * Created by apapad on 15/11/15.
 */
public class PopularMoviesFetcher extends JSONFetcher {
    private MovieJSONParser parser = new MovieJSONParser();


    public MovieList fetch(final SortByCriterion sortBy, final int page) throws IOException {
        URL url = URLBuilder.buildPopularMoviesURL(sortBy, page);
        String jsonString = getJSON(url);
        return parser.parseMovieList(jsonString);
    }


}
