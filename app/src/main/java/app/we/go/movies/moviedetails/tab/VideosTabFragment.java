package app.we.go.movies.moviedetails.tab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.moviedetails.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import app.we.go.movies.remote.json.Video;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 *
 * Created by apapad on 26/02/16.
 */
public class VideosTabFragment extends Fragment implements MovieDetailsContract.VideosView,
        VideoArrayAdapter.VideoClickListener{


    @Inject
    MovieDetailsContract.VideosPresenter presenter;


    @Inject
    Context context;

    @Bind(R.id.videos_list)
    ListView listView;

    @Bind(R.id.videos_list_empty)
    TextView emptyView;


    private ArrayAdapter<Video> adapter;

    public static VideosTabFragment newInstance(long movieId) {
        VideosTabFragment f = new VideosTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        Log.d("ZZ", "Args " + movieId);
        return f;
    }


    @Override
    @DebugLog
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_video_tab, container, false);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindView(this);

        adapter = new VideoArrayAdapter(context, R.layout.video_row, new ArrayList<Video>(), getActivity().getLayoutInflater(), this);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        presenter.loadMovieVideos(getArguments().getLong(Args.ARG_MOVIE_ID));
    }




    @Override
    public void displayVideos(List<Video> videos) {
        adapter.clear();
        adapter.addAll(videos);
    }

    @Override
    public void displayError(@StringRes int errorMessage) {
        // Do nothing, do not display the error message, just leave the empty list message
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
}
