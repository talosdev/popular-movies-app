package com.talosdev.movies.remote.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apapad on 13/11/15.
 */
public class Movie {

    @SerializedName("id")
    public long id;

    @SerializedName("original_title")
    public String title;

    @SerializedName("overview")
    public String overview;

    //TODO
    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public long voteCount;



}