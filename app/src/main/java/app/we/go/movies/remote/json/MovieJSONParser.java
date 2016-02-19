package app.we.go.movies.remote.json;

import com.google.gson.Gson;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParser {


    private Gson gson;

    public MovieJSONParser() {
        gson = new MovieGsonBuilder().create();
    }

    public Movie parseMovie(String json) {
        Movie movie = gson.fromJson(json, Movie.class);
        return movie;
    }


    public MovieList parseMovieList(String json) {
        MovieList list = gson.fromJson(json, MovieList.class);
        return list;
    }

}
