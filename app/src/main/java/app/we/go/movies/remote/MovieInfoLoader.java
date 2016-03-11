package app.we.go.movies.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.Movie;

/**
 * Created by apapad on 2/03/16.
 */
public class MovieInfoLoader extends AsyncTaskLoader<Movie> {

    private final long movieId;
    private MovieDetailsFetcher fetcher;
    private Movie data;

    public MovieInfoLoader(Context context, TMDBService service, long id) {
        super(context);
        movieId = id;
        fetcher = new MovieDetailsFetcher(service);
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
    public void deliverResult(Movie data) {
        // We’ll save the data for later retrieval
        this.data = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }

    @Override
    public Movie loadInBackground() {
        try {
            Movie movie = fetcher.fetch(movieId);
            data = movie;
            return data;
        } catch (IOException e) {
            Log.e(Tags.REMOTE, String.format("There was an error fetching details for movie %d", movieId), e);
        }
        return null;
    }
}
