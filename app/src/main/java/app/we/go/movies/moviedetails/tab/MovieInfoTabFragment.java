package app.we.go.movies.moviedetails.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.common.BaseView;
import app.we.go.movies.constants.Args;
import app.we.go.movies.moviedetails.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import app.we.go.movies.remote.json.Movie;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieInfoTabFragment extends Fragment implements MovieDetailsContract.InfoView {

    @Bind(R.id.release_date)
    TextView releaseDateView;

    @Bind(R.id.vote_average)
    TextView voteAverageView;

    @Bind(R.id.vote_count)
    TextView voteCountView;

    @Bind(R.id.synopsis)
    TextView descriptionView;

    @Inject
    MovieDetailsContract.Presenter presenter;


    public static MovieInfoTabFragment newInstance(long movieId) {
        MovieInfoTabFragment f = new MovieInfoTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO review this
        // container might be null when there is a configuration change from
        // two-pane to one-pane, and this fragment is reloaded, but without container
        if (container == null) {
            return null;
        } else {
            return inflater.inflate(R.layout.movie_details_info_tab, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindInfoView(this);
        presenter.loadMovieInfo(getArguments().getLong(Args.ARG_MOVIE_ID));

    }

    @Override
    public void displayInfo(Movie movie) {
        descriptionView.setText(movie.getOverview());

        voteAverageView.setText(movie.getVoteAverage() + "");
        voteCountView.setText("(" + movie.getVoteCount() + " votes)");
    }

    @Override
    public void displayFormattedDate(String date) {
        releaseDateView.setText(date);
    }

    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
        BaseView.Helper.showError(getContext(), logMessage, resourceId, t);
    }
}
