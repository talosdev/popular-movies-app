package com.talosdev.movies.remote.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talosdev.movies.constants.TMDB;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParser {


    private Gson gson;

    public MovieJSONParser() {
        gson = new GsonBuilder().setDateFormat(TMDB.DATE_FORMAT).create();
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
