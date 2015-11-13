package talosdev.movies.remote;

import com.google.gson.Gson;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParser {



    public Movie parse(String json) {

        Gson gson = new Gson();
        Movie movie = gson.fromJson(json, Movie.class);
        return movie;

    }

}
