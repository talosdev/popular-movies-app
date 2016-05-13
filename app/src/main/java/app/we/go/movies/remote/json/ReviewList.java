package app.we.go.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by apapad on 1/03/16.
 */
public class ReviewList {

    public ReviewList() {
    }

    private long id;

    private int page;

    @SerializedName("results")
    private ArrayList<Review> reviews;

    @SerializedName("total_pages")
    private int totalPages;


    @SerializedName("total_results")
    private int totalResults;

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
