package com.talosdev.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Java object to which the json response with a list of  movies is deserialized.
 * Created by apapad on 13/11/15.
 */
public class MovieList {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public List<Movie> movies;

    @SerializedName("total_pages")
    public long totalPages;

    @SerializedName("total_results")
    public long totalResults;
}
