package com.talosdev.movies.constants;

import com.talosdev.movies.BuildConfig;

/**
 * Created by apapad on 26/11/15.
 */
public class TMDB {

    /**
     * The api key, taken from an environment property, see the gradle build file
     */
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    /**
     * The name of the api key parameter for building URLs
     */
    public static final String PARAM_API_KEY = "api_key";


    /**
     * The base url where the TMDB api can be reached
     */
    public final static String BASE_URL = "https://api.themoviedb.org";

    /**
     * The URL for getting the list of popular movies
     */
    public static final String URL_MOVIES = BASE_URL + "/3/discover/movie";

    /**
     * The URL for getting the details of a movie
     */
    public static final String URL_MOVIE_DETAILS = BASE_URL + "/3/movie/";

    /**
     * The URL for getting the poster image
     */
    private static final String POSTER_BASE_URL_154 = "http://image.tmdb.org/t/p/w154/";


    /**
     * The URL for getting the backdrop image
     */
    private static final String BACKDROP_BASE_URL_780 = "http://image.tmdb.org/t/p/w780/";

    private static final String BACKDROP_BASE_URL_1280 = "http://image.tmdb.org/t/p/w1280/";
    /**
     * The number of movies that are included in each page of results returned by the
     * discover queries.
     */
    public static final int MOVIES_PER_PAGE = 20;


    public static String buildPosterUrl(String poster) {
        return POSTER_BASE_URL_154 + poster;
    }


    public static String buildBackdropUrl(String backdrop) {
        return BACKDROP_BASE_URL_1280 + backdrop;
    }

}
