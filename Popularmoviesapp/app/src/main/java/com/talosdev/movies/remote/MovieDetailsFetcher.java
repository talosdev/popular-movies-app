package com.talosdev.movies.remote;

import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieJSONParser;

import java.io.IOException;
import java.net.URL;

/**
 * Class that encapsulates the code for fetching the details for a movie from TMDB API
 * Created by apapad on 26/11/15.
 */
public class MovieDetailsFetcher extends JSONFetcher {
    private MovieJSONParser parser = new MovieJSONParser();


    public Movie fetch(String id) throws IOException {

        URL url = getMovieDetailsUrl(id);
        String jsonString = getJSON(url);
        if (jsonString == null) return null;
        return parser.parseMovie(jsonString);
    }

}
