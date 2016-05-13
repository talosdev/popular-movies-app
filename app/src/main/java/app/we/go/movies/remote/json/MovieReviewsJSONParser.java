package app.we.go.movies.remote.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by apapad on 1/03/16.
 */
public class MovieReviewsJSONParser {

    private Gson gson;

    public MovieReviewsJSONParser() {
        gson = new GsonBuilder().create();
    }



    public ReviewList parse(String json) {
        ReviewList list = gson.fromJson(json, ReviewList.class);
        return list;
    }


    public String toJson(Review review) {
        return gson.toJson(review);
    }

    public String toJson(ReviewList reviewList) {
        return gson.toJson(reviewList);
    }

}
