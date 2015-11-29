package com.talosdev.movies.remote;

import android.net.Uri;

import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.SortByCriterion;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by apapad on 29/11/15.
 */
public class URLBuilder {

    public static URL buildPopularMoviesURL(SortByCriterion sortBy) throws MalformedURLException {
        Uri uri = Uri.parse(TMDB.URL_MOVIES).buildUpon().
                appendQueryParameter("sort_by", convertSortByCriterionToStringParameter(sortBy)).
                appendQueryParameter(TMDB.PARAM_API_KEY, TMDB.API_KEY).build();

        URL url = new URL(uri.toString());
        return url;
    }

    /**
     * Convert the enum value to the parameter value that the API expects
     *
     * @param sortBy
     * @return
     */
    private static String convertSortByCriterionToStringParameter(SortByCriterion sortBy) {
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
