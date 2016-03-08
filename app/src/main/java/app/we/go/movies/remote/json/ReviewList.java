package app.we.go.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by apapad on 1/03/16.
 */
public class ReviewList {

    public ReviewList() {
    }

    public long id;

    public int page;

    @SerializedName("results")
    public ArrayList<Review> reviews;

    @SerializedName("total_pages")
    public int totalPages;


    @SerializedName("total_results")
    public int totalResults;

}
