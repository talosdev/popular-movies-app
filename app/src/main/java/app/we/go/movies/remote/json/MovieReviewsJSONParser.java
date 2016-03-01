package app.we.go.movies.remote.json;

import com.google.gson.Gson;

/**
 * Created by apapad on 1/03/16.
 */
public class MovieReviewsJSONParser {

    private Gson gson;

    public MovieReviewsJSONParser() {
        gson = new MovieGsonBuilder().create();
    }



    public ReviewList parse(String json) {
        ReviewList list = gson.fromJson(json, ReviewList.class);
        return list;
    }

}
