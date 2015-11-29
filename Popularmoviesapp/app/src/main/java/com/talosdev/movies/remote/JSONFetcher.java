package com.talosdev.movies.remote;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.talosdev.movies.constants.TMDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by apapad on 29/11/15.
 */
public abstract class JSONFetcher {

    private final static String LOG_TAG = "REMOTE";

    @Nullable
    protected String getJSON(URL url) throws IOException {
        String jsonString;
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
        return jsonString;
    }

    @NonNull
    protected URL getMovieDetailsUrl(String id) throws MalformedURLException {
        Uri uri = Uri.parse(TMDB.URL_MOVIE_DETAILS).buildUpon().
                appendEncodedPath(id).
                appendQueryParameter(TMDB.PARAM_API_KEY, TMDB.API_KEY).build();

        return new URL(uri.toString());
    }
}
