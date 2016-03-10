package app.we.go.movies.ui.tab;

import android.content.Context;
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

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.VideoAsyncLoader;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.ui.MovieApplication;
import hugo.weaving.DebugLog;

/**
 * See {@link MovieReviewsTabFragment} for some notes.
 *
 * Created by apapad on 26/02/16.
 */
public class VideosTabFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Video>>, AdapterView.OnItemClickListener {

    private static final int LOADER_VIDEOS = 3;

    @Inject
    URLBuilder urlBuilder;

    @Inject
    TMDBService service;

    public static VideosTabFragment newInstance(long movieId) {
        VideosTabFragment f = new VideosTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        Log.d("ZZ", "Args " + movieId);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MovieApplication) context.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    @DebugLog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = new VideoArrayAdapter(getActivity(), R.layout.video_row, new ArrayList<Video>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);


        getListView().setOnItemClickListener(this);


        // Workaround for https://code.google.com/p/android/issues/detail?id=69586
        // Loader in off-screen tabs won't start if the first tab contains a loader.
        getView().post(new Runnable() {
            @Override
            public void run() {
                setUserVisibleHint(true);
            }
        });
    }


    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LOADER_VIDEOS, null, this).startLoading();

    }



    @DebugLog
    @Override
    public Loader<List<Video>> onCreateLoader(int id, Bundle args) {
        return new VideoAsyncLoader(getActivity(), service,
                getArguments().getLong(Args.ARG_MOVIE_ID));
    }

    @DebugLog
    @Override
    public void onLoadFinished(Loader<List<Video>> loader, List<Video> data) {
        if (data != null) {
            ((ArrayAdapter) getListAdapter()).clear();
            ((ArrayAdapter) getListAdapter()).addAll(data);
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
