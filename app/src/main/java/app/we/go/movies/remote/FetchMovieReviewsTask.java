package app.we.go.movies.remote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.listener.MovieReviewsListener;
import app.we.go.movies.remote.json.ReviewList;

/**
 * Created by apapad on 01/03/2016
 */
public class FetchMovieReviewsTask extends AsyncTask<Long, Void, ReviewList> {


    private final MovieReviewsListener listener;

    public FetchMovieReviewsTask(MovieReviewsListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ReviewList result) {
        super.onPostExecute(result);
        if (result != null) {

            Log.d("UI", "Received reviewsList with " + result.reviews.size() + " items. Notifying the adapter...");

            listener.onMovieReviewsReceived(result.reviews);

        }
    }

    @Override
    protected ReviewList doInBackground(Long... params) {
        long movieId = params[0];

        try {

            MovieReviewsFetcher fetcher = new MovieReviewsFetcher();
            ReviewList reviewList = fetcher.fetch(movieId);

            return reviewList;

        } catch (IOException e) {
            Log.e(Tags.REMOTE, "Error ", e);
            return null;
        }
    }
}