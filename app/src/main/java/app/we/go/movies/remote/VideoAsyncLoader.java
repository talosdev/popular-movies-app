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

    public VideoAsyncLoader(Context context, long id) {
        super(context);
        movieId = id;
        fetcher = new VideosFetcher();
    }

    @Override
    public List<Video> loadInBackground() {
        try {
            VideoList vList =  fetcher.fetch(movieId);
            if (vList != null) {
                return vList.videos;
            }
        } catch (IOException e) {
            Log.e(Tags.REMOTE, String.format("There was an error fetching reviews for movie %d", movieId), e);
        }
        return null;
    }
}
