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

    private static final String PARAM_SORT_BY = "sort_by";
    private static final String PARAM_PAGE = "page";

    private static final int[] BACKDROP_RESOLUTIONS = new int[]{1280, 780, 300};


    public static URL buildPopularMoviesURL(SortByCriterion sortBy, int page) throws MalformedURLException {
        Uri uri = Uri.parse(TMDB.URL_MOVIES).buildUpon().
                appendQueryParameter(PARAM_SORT_BY, convertSortByCriterionToStringParameter(sortBy)).
                appendQueryParameter(PARAM_PAGE, page + "").
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

    public static String buildBackdropPath(String backdrop, int imageViewWidth) {
        if (backdrop == null || backdrop.equals("") || backdrop.equals("null")) {
            return null;
        }

        int tmdbResolution = calculateBackdropResolutionToDownload(imageViewWidth);
        return TMDB.BACKDROP_BASE_URL + "/w" + tmdbResolution + backdrop;
    }


    /**
     * Calculates the resolution of the backdrop image to get from the API, taking into account
     * the available space in the UI
     *
     * @param imageViewWidth in pixels
     * @return
     */
    private static int calculateBackdropResolutionToDownload(int imageViewWidth) {

        for (int i = 0; i < BACKDROP_RESOLUTIONS.length; i++) {
            if (imageViewWidth >= BACKDROP_RESOLUTIONS[i]) {
                return BACKDROP_RESOLUTIONS[i];
            }
        }
        return BACKDROP_RESOLUTIONS[BACKDROP_RESOLUTIONS.length - 1];

    }

}
