package app.we.go.movies.model.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apapad on 1/03/16.
 */
public class ReviewList {

    public ReviewList() {
    }

    private long id;

    private int page;

    @SerializedName("results")
    private List<Review> reviews;

    @SerializedName("total_pages")
    private int totalPages;


    @SerializedName("total_results")
    private int totalResults;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
