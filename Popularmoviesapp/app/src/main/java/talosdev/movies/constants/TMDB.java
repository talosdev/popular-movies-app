package talosdev.movies.constants;

import talosdev.movies.BuildConfig;

/**
 * Created by apapad on 26/11/15.
 */
public class TMDB {
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;

    public final static String BASE_URL = "https://api.themoviedb.org";
    public static final String PARAM_API_KEY = "api_key";
    public static final String URL_MOVIE_DETAILS = BASE_URL + "/3/movie/";
    public static final String URL_MOVIES = BASE_URL + "/3/discover/movie";
    public static final String IMAGE_BASE_URL_154 = "http://image.tmdb.org/t/p/w154/";


    public static String buildPosterUrl(String poster) {
        return IMAGE_BASE_URL_154 + poster;
    }

}
