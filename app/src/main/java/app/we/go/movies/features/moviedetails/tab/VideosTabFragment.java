package app.we.go.movies.features.moviedetails.tab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.we.go.framework.mvp.view.CacheablePresenterBasedFragment;
import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.constants.Args;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.dependency.MovieVideosModule;
import app.we.go.movies.model.remote.Video;
import butterknife.BindView;
import hugo.weaving.DebugLog;

/**
 *
 * Created by apapad on 26/02/16.
 */
public class VideosTabFragment extends CacheablePresenterBasedFragment<MovieDetailsContract.VideosPresenter>
        implements MovieDetailsContract.VideosView,
        VideoArrayAdapter.VideoClickListener{


    @BindView(R.id.videos_list)
    ListView listView;

    @BindView(R.id.videos_list_empty)
    TextView emptyView;

    private ArrayAdapter<Video> adapter;


    public static VideosTabFragment newInstance(long movieId) {
        VideosTabFragment f = new VideosTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }


    @Override
    @DebugLog
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details_video_tab, container, false);

    }


    @Override
    protected void injectDependencies(String presenterTag) {
        MovieApplication.get(getActivity()).
                getComponent().
                plus(new MovieVideosModule(getActivity(), presenterTag)).
                inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new VideoArrayAdapter(context, R.layout.video_row, new ArrayList<Video>(), getActivity().getLayoutInflater(), this);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }


    @Override
    protected void initViewNoCache() {
        presenter.loadMovieVideos(getArguments().getLong(Args.ARG_MOVIE_ID));
    }


    @Override
    public void displayVideos(List<Video> videos) {
        adapter.clear();
        adapter.addAll(videos);
    }


    @Override
    public void openVideo(Uri videoUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(videoUrl);
        startActivity(i);
    }

    @Override
    public void shareVideo(Uri videoUrl, String videoName) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, videoName);
        i.putExtra(Intent.EXTRA_TEXT, videoUrl);
        context.startActivity(Intent.createChooser(i,
               context.getString(R.string.share_chooser_title)));
    }


    @Override
    public void onVideoDetailsClick(String videoKey) {
        presenter.onVideoClicked(videoKey);
    }

    @Override
    public void onVideoShareClick(String videoKey, String videoName) {
        presenter.onShareVideoClicked(videoKey, videoName);
    }

    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
        // Do nothing, do not display the error message, just leave the empty list message
    }


}
