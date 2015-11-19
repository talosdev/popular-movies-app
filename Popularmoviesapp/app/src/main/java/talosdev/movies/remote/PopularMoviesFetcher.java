package talosdev.movies.remote;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import talosdev.movies.BuildConfig;
import talosdev.movies.data.SortByCriterion;
import talosdev.movies.remote.json.MovieJSONParser;
import talosdev.movies.remote.json.MovieList;

/**
 * Class that encapsulates the code for fetching the movies from TMDB API
 * Created by apapad on 15/11/15.
 */
public class PopularMoviesFetcher {
    private final static String LOG_TAG = "REMOTE";
    private final static String API_KEY = BuildConfig.TMDB_API_KEY;
    private final static String BASE_URL = "https://api.themoviedb.org";
    private MovieJSONParser parser = new MovieJSONParser();




    public MovieList fetch(SortByCriterion sortBy) throws IOException {


        String jsonString;

        Uri uri = Uri.parse(BASE_URL).buildUpon().
                encodedPath("/3/discover/movie").
                appendQueryParameter("sort_by", convertSortByCriterionToStringParameter(sortBy)).
                appendQueryParameter("api_key", API_KEY).build();

        URL url = new URL(uri.toString());
        Log.d(LOG_TAG, "Contacting url: " + url.toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // Read the input stream into a String
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
            // But it does make debugging a *lot* easier if you print out the completed
            // buffer for debugging.
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }
        jsonString = buffer.toString();
        return parser.parseMovieList(jsonString);
    }

    /**
     * Convert the enum value to the parameter value that the API expects
     * @param sortBy
     * @return
     */
    private String convertSortByCriterionToStringParameter (SortByCriterion sortBy) {
        switch (sortBy) {
            case POPULARITY:
                return "popularity.desc";
            case VOTE:
                return "vote_average.desc";
            default:
                return "";
        }
    }
}
