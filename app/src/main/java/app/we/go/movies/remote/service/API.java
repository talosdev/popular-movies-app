package app.we.go.movies.remote.service;

import android.support.annotation.Nullable;

import app.we.go.movies.model.local.SortByCriterion;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class API {

    public static final String POPULARITY_DESC = "popularity.desc";
    public static final String VOTE_AVERAGE_DESC = "vote_average.desc";

    /**
     * Convert the enum value to the parameter value that the API expects
     *
     * @param sortBy
     * @return
     */
    public static String sortByCriterionToString(SortByCriterion sortBy) {
        switch (sortBy) {
            case POPULARITY:
                return POPULARITY_DESC;
            case VOTE:
                return VOTE_AVERAGE_DESC;
            default:
                return "";
        }
    }

    /**
     * Convert a String to an enum value {@link SortByCriterion}
     * @param sortBy
     * @return
     */
    @Nullable
    public static SortByCriterion stringToSortByCriterion(String sortBy) {
        for (SortByCriterion criterion: SortByCriterion.values()) {
            if (sortBy.equals(sortByCriterionToString(criterion))) {
                return criterion;
            }
        }
        return null;
    }
}
