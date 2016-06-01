package app.we.go.movies.constants;

import app.we.go.movies.BuildConfig;

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
    public final static String BASE_URL = "https://api.themoviedb.org/3/";

    /**
     * The URL for getting the list of popular movies
     */
    public static final String URL_MOVIES = BASE_URL + "discover/movie";



    public static final String DATE_FORMAT = "yyyy-MM-dd";


    /**
     * The base URL for getting the poster image
     */
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p";


    /**
     * The base URL for getting the backdrop image
     */
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p";

    /**
     * The number of movies that are included in each page of results returned by the
     * discover queries.
     */
    public static final int MOVIES_PER_PAGE = 20;


    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";
    public static final String YOUTUBE_VIDEO_PARAM = "v";
    public static final String PARAM_VOTE_COUNT_MINIMUM = "vote_count.gte";
}
