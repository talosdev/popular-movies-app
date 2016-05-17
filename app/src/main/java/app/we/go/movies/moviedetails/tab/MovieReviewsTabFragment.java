package app.we.go.movies.moviedetails.tab;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
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
import app.we.go.movies.remote.json.Review;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 *
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends Fragment implements MovieDetailsContract.ReviewsView {


    @Inject
    MovieDetailsContract.ReviewsPresenter presenter;

    @Bind(R.id.reviews_list)
    ListView listView;

    @Bind(R.id.reviews_list_empty)
    TextView emptyView;


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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindView(this);

        adapter = new ReviewsArrayAdapter(getActivity(), R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        listView.setOnItemClickListener(null);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        presenter.loadMovieReviews(getArguments().getLong(Args.ARG_MOVIE_ID));
    }





    @Override
    public void displayReviews(List<Review> reviews) {
        adapter.addAll(reviews);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(@StringRes int errorMessage) {
        // Do nothing, do not display the error message, just leave the empty list message
    }
}
