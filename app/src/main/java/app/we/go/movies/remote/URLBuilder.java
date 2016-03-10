package app.we.go.movies.remote;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.data.SortByCriterion;

/**
 * Created by apapad on 29/11/15.
 */
public class URLBuilder {

    private static final String PARAM_SORT_BY = "sort_by";
    private static final String PARAM_PAGE = "page";

    private static final int[] BACKDROP_RESOLUTIONS = new int[]{300, 780, 1280};

    private static final int[] POSTER_RESOLUTIONS = new int[]{92, 154, 185, 342, 500, 780};

    public URLBuilder() {
    }


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

        if (imageViewWidth < BACKDROP_RESOLUTIONS[0]) {
            return BACKDROP_RESOLUTIONS[0];
        }

        for (int i = 1; i < BACKDROP_RESOLUTIONS.length - 1; i++) {
            if (imageViewWidth <= BACKDROP_RESOLUTIONS[i]) {
                return BACKDROP_RESOLUTIONS[i];
            }
        }
        return BACKDROP_RESOLUTIONS[BACKDROP_RESOLUTIONS.length - 1];

    }




    public Uri buildYoutubeUri(String key)   {
        Uri uri = Uri.parse(TMDB.YOUTUBE_BASE_URL).buildUpon().
                appendQueryParameter(TMDB.YOUTUBE_VIDEO_PARAM, key).build();
        return uri;
    }


    /**
     *
     * @param poster
     * @param posterWidth
     *  in pixels
     * @return
     */
    @Nullable
    public String buildPosterUrl(String poster, int posterWidth) {
        if (poster == null || poster.equals("") || poster.equals("null")) {
            return null;
        }
        int tmdbResolution = calculatePosterResolutionToDownload(posterWidth);
        return TMDB.POSTER_BASE_URL + "/w" + tmdbResolution + poster;
    }

    private  int calculatePosterResolutionToDownload(int posterWidth) {
        if (posterWidth < POSTER_RESOLUTIONS[0]) {
            return POSTER_RESOLUTIONS[0];
        }

        for (int i = 1; i < POSTER_RESOLUTIONS.length - 1; i++) {
            if (posterWidth <= POSTER_RESOLUTIONS[i]) {
                return POSTER_RESOLUTIONS[i];
            }
        }
        return POSTER_RESOLUTIONS[POSTER_RESOLUTIONS.length - 1];
    }

}
