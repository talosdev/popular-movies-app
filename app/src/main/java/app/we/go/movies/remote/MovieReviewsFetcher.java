package app.we.go.movies.remote;

import android.util.Log;

import java.io.IOException;
import java.net.URL;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.MovieReviewsJSONParser;
import app.we.go.movies.remote.json.ReviewList;

/**
 * Class that encapsulates the code for fetching the details for a movie from TMDB API
 * Created by apapad on 26/11/15.
 */
public class MovieReviewsFetcher extends JSONFetcher {
    private MovieReviewsJSONParser parser = new MovieReviewsJSONParser();
    private URLBuilder urlBuilder;

    public MovieReviewsFetcher() {
        urlBuilder = new URLBuilder();

    }

    public ReviewList fetch(long id) throws IOException {

        URL url = urlBuilder.buildMovieReviewsUrl(id);
        Log.d(Tags.REMOTE, String.format("Requesting reviews from url: %s", url));
        String jsonString = getJSON(url);
        if (jsonString == null) return null;
        return parser.parse(jsonString);
    }

}
