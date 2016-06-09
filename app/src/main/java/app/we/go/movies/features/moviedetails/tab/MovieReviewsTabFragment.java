package app.we.go.movies.features.moviedetails.tab;

import android.content.Context;
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

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.constants.Args;
import app.we.go.movies.features.moviedetails.MovieDetailsContract;
import app.we.go.movies.features.moviedetails.dependency.MovieReviewsModule;
import app.we.go.movies.model.remote.Review;
import app.we.go.framework.mvp.view.CacheablePresenterBasedFragment;
import butterknife.Bind;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends CacheablePresenterBasedFragment<MovieDetailsContract.ReviewsPresenter>
        implements MovieDetailsContract.ReviewsView {


    @Bind(R.id.reviews_list)
    ListView listView;

    @Bind(R.id.reviews_list_empty)
    TextView emptyView;


    @Inject
    Context context;

    private ArrayAdapter<Review> adapter;


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
    protected void injectDependencies(String presenterTag) {
         MovieApplication.get(getActivity()).
                getComponent().
                plus(new MovieReviewsModule(getActivity(), presenterTag)).inject(this);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ReviewsArrayAdapter(context, R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        listView.setOnItemClickListener(null);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }


    @Override
    public void displayReviews(List<Review> reviews) {
        adapter.addAll(reviews);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void initViewNoCache() {
        presenter.loadMovieReviews(getArguments().getLong(Args.ARG_MOVIE_ID));
    }

    @Override
    public void showError(Context context, String logMessage, int resourceId, @Nullable Throwable t) {
        // Do nothing, do not display the error message, just leave the empty list message

    }


}