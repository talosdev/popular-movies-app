package com.talosdev.movies.remote.json;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Java object to which the json response with details about a movie is deserialized.
 * Created by apapad on 13/11/15.
 */
public class Movie {

    @SerializedName("id")
    public long id;

    @SerializedName("original_title")
    public String title;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public Date releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public long voteCount;



}
