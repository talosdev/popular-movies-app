package talosdev.movies.remote.json;

import com.google.gson.Gson;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParser {


    public Movie parseMovie(String json) {
        Gson gson = new Gson();
        Movie movie = gson.fromJson(json, Movie.class);
        return movie;
    }


    public MovieList parseMovieList(String json) {
        Gson gson = new Gson();
        MovieList list = gson.fromJson(json, MovieList.class);
        return list;
    }

}
