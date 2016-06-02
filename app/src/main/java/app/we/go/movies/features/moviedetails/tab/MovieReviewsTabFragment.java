package app.we.go.movies.features.moviedetails.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.constants.Args;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.dependency.MovieReviewsModule;
import app.we.go.movies.model.remote.Review;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends Fragment implements MovieDetailsContract.ReviewsView {


    @Inject
    MovieDetailsContract.ReviewsPresenter presenter;

    @Bind(R.id.reviews_list)
    ListView listView;

    @Bind(R.id.reviews_list_empty)
    TextView emptyView;


    @Inject
    Context context;

    private ArrayAdapter<Review> adapter;

    private String presenterTag;
    private boolean isDestroyedBySystem;
    private boolean fromCache;


    public MovieReviewsTabFragment() {
    }

    public static MovieReviewsTabFragment newInstance(long movieId) {
        MovieReviewsTabFragment f = new MovieReviewsTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }


    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details_review_tab, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            presenterTag = savedInstanceState.getString(Tags.PRESENTER_TAG);
            fromCache = true;
        }
        if (presenterTag == null) {
            presenterTag = UUID.randomUUID().toString();
        }

        MovieApplication.get(getActivity()).
                getComponent().
                plus(new MovieReviewsModule(getActivity(), presenterTag)).
                inject(this);

        presenter.bindView(this);

        adapter = new ReviewsArrayAdapter(context, R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        listView.setOnItemClickListener(null);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (fromCache) {
            presenter.onRestoreFromCache();
        } else {
            presenter.loadMovieReviews(getArguments().getLong(Args.ARG_MOVIE_ID));
        }
    }


    @Override
    public void displayReviews(List<Review> reviews) {
        adapter.addAll(reviews);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
        // Do nothing, do not display the error message, just leave the empty list message

    }

    @Override
    public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
        outState.putString(Tags.PRESENTER_TAG, presenterTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem) {
            presenter.clear();
        }
    }
}
