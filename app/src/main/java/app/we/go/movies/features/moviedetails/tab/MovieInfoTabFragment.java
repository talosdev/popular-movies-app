package app.we.go.movies.features.moviedetails.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.we.go.framework.mvp.view.CacheablePresenterBasedFragment;
import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.constants.Args;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.dependency.HasDetailsServiceModule;
import app.we.go.movies.features.moviedetails.dependency.MovieInfoModule;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.util.LOG;
import butterknife.Bind;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieInfoTabFragment extends CacheablePresenterBasedFragment<MovieDetailsContract.MovieInfoPresenter>
        implements MovieDetailsContract.InfoView {

    @Bind(R.id.release_date)
    TextView releaseDateView;

    @Bind(R.id.vote_average)
    TextView voteAverageView;

    @Bind(R.id.vote_count)
    TextView voteCountView;

    @Bind(R.id.synopsis)
    TextView descriptionView;


    public static MovieInfoTabFragment newInstance(long movieId) {
        MovieInfoTabFragment f = new MovieInfoTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }



    @Override
    protected void injectDependencies(String presenterTag) {
        MovieApplication.get(getActivity()).getComponent().
                plus(((HasDetailsServiceModule) getActivity()).getModule(),
                        new MovieInfoModule(presenterTag)).inject(this);

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
    protected void initViewNoCache() {
        long movieId = getArguments().getLong(Args.ARG_MOVIE_ID);
        LOG.d(Tags.REMOTE, "Fetching movie details for: %d", movieId);
        presenter.loadMovieInfo(movieId);
    }

    @Override
    public void displayInfo(Movie movie) {
        LOG.d(Tags.REMOTE, "Got info details for movie %d, and will display them", movie.getId());
        descriptionView.setText(movie.getOverview());

        voteAverageView.setText(movie.getVoteAverage() + "");

        voteCountView.setText(
                String.format(context.getResources().getString(R.string.votes),
                        movie.getVoteCount()));
    }

    @Override
    public void displayFormattedDate(String date) {
        releaseDateView.setText(date);
    }

    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
//        showError(getContext(), logMessage, resourceId, t);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }
}
