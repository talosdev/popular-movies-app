package app.we.go.movies.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoAsyncLoader extends AsyncTaskLoader<List<Video>> {

    private final long movieId;
    private VideosFetcher fetcher;
    private List<Video> data;

    public VideoAsyncLoader(Context context, long id) {
        super(context);
        movieId = id;
        fetcher = new VideosFetcher();
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
    public void deliverResult(List<Video> data) {
        // We’ll save the data for later retrieval
        this.data = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }

    @Override
    public List<Video> loadInBackground() {
        try {
            VideoList vList =  fetcher.fetch(movieId);
            if (vList != null) {
                data = vList.videos;
                return data;
            }
        } catch (IOException e) {
            Log.e(Tags.REMOTE, String.format("There was an error fetching reviews for movie %d", movieId), e);
        }
        return null;
    }
}
