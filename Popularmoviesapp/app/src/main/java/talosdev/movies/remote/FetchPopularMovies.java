package talosdev.movies.remote;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import talosdev.movies.BuildConfig;
import talosdev.movies.data.SortByCriterion;
import talosdev.movies.remote.json.Movie;

/**
 * Created by apapad on 13/11/15.
 */
public class FetchPopularMovies  extends AsyncTask<SortByCriterion, Void, List<Movie>> {

    private static String API_KEY = BuildConfig.TMDB_API_KEY;
    private static String BASE_URL = "https://api.themoviedb.org/3";
    private String LOG_TAG = "REMOTE";




    public FetchPopularMovies() {
        super();

    }

    @Override
    protected List<Movie> doInBackground(SortByCriterion... params) {

        SortByCriterion sortBy = null;
        if (params.length == 0) {
            sortBy = SortByCriterion.POPULARITY;
        } else {
            sortBy = params[0];
        }

        String jsonString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            Uri uri = Uri.parse(BASE_URL).buildUpon().
                    appendPath("/discovery/movies").
                    appendQueryParameter("sort_by", convertSortByCriterionToStringParameter(sortBy)).
                    appendQueryParameter("api_key", API_KEY).build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

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

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }

        return null;
    }

    private String convertSortByCriterionToStringParameter (SortByCriterion sortBy) {
        switch (sortBy) {
            case POPULARITY:
                return "popularity.desc";
            case VOTE:
                return "vote_count.desc";
            default:
                return "";
        }
    }
}
