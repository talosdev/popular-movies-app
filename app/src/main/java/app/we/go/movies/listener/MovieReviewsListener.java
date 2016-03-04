package app.we.go.movies.listener;

import java.util.ArrayList;

import app.we.go.movies.remote.json.Review;

/**
 * Callback that is called when the trailer list for a movie is received.
 * Created by apapad on 29/02/16.
 */
public interface MovieReviewsListener {

    void onMovieReviewsReceived(ArrayList<Review> reviews);
}
