package app.we.go.movies.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;

/**
 * Created by apapad on 2/03/16.
 */
public class ReviewsAsyncLoader extends AsyncTaskLoader<List<Review>> {

    private final long movieId;
    private MovieReviewsFetcher fetcher;
    private List<Review> data;

    public ReviewsAsyncLoader(Context context, MovieReviewsFetcher fetcher, long id) {
        super(context);
        movieId = id;
        this.fetcher = fetcher;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            // Use cached data
            deliverResult(data);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<Review> data) {
        // We’ll save the data for later retrieval
        this.data = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }

    @Override
    public List<Review> loadInBackground() {
        try {
            ReviewList rList =  fetcher.fetch(movieId);
            if (rList != null) {
                data = rList.reviews;
                return data;
            }
        } catch (IOException e) {
            Log.e(Tags.REMOTE, String.format("There was an error fetching reviews for movie %d", movieId), e);
        }
        return null;
    }
}
