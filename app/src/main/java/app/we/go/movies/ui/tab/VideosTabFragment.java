package app.we.go.movies.ui.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.listener.MovieVideosListener;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.VideoAsyncLoader;
import app.we.go.movies.remote.json.Video;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class VideosTabFragment extends ListFragment implements MovieVideosListener, LoaderManager.LoaderCallbacks, AdapterView.OnItemClickListener {


    private static final String BUNDLE_VIDEOS = "app.we.go.movies.BUNDLE_VIDEOS";
    private URLBuilder urlBuilder;
    private ArrayList<Video> currentVideos;
    private long currentMovie;

    public static VideosTabFragment newInstance(long movieId) {
        VideosTabFragment f = new VideosTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        Log.d("ZZ", "Args " + movieId);
        return f;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_VIDEOS, currentVideos);
    }

    @Override
    @DebugLog
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_video_tab, container, false);

        return v;
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    @DebugLog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlBuilder = new URLBuilder();
    }


    // TODO why onActivityCreated?
    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new VideoArrayAdapter(getActivity(), R.layout.video_row, new ArrayList<Video>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);
        getListView().setOnItemClickListener(this);


        // Restore state strategy: first check instance variable (back button), then check bundle
        // (orientation change), and if not fetch from the network.
        Log.d("ZZ", "Args " + getArguments().getLong(Args.ARG_MOVIE_ID) + " - currentMovie " + currentMovie);

        if (currentVideos != null) {
            // if the movie has changes, (replacing fragments in two-pane mode) we need to skip this
            // and fetch from the network
            if (currentMovie == getArguments().getLong(Args.ARG_MOVIE_ID)) {
                onMovieVideosReceived(currentVideos);
            }
        } else if (savedInstanceState != null) {
            ArrayList<Video> videosFromBundle = savedInstanceState.getParcelableArrayList(BUNDLE_VIDEOS);
            if (videosFromBundle != null) {
                onMovieVideosReceived(videosFromBundle);
            }
        } else {
            getLoaderManager().getLoader(1).forceLoad();
        }
    }

    @Override
    public void onMovieVideosReceived(ArrayList<Video> videos) {
        currentVideos = videos;
        // currentMovie assignment must happen only here (and not in onLoadFinished)
        currentMovie = getArguments().getLong(Args.ARG_MOVIE_ID);
        notifyAdapter(videos);
    }

    private void notifyAdapter(ArrayList<Video> videos) {
        currentVideos = videos;
        ((ArrayAdapter) getListAdapter()).clear();
        ((ArrayAdapter) getListAdapter()).addAll(videos);
    }


    @DebugLog
    @Override
    public Loader<List<Video>> onCreateLoader(int id, Bundle args) {
        return new VideoAsyncLoader(getActivity(),
                getArguments().getLong(Args.ARG_MOVIE_ID));
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (data != null) {
            notifyAdapter((ArrayList<Video>) data);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        ((ArrayAdapter) getListAdapter()).clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String videoKey = ((Video) getListAdapter().getItem(position)).key;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(urlBuilder.buildYoutubeUri(videoKey));
        startActivity(i);
    }
}
