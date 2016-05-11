package app.we.go.movies.moviedetails.tab;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.moviedetails.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import app.we.go.movies.remote.json.Review;
import hugo.weaving.DebugLog;

/**
 *
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends ListFragment implements MovieDetailsContract.ReviewsView {


    @Inject
    MovieDetailsContract.ReviewsPresenter presenter;

    public MovieReviewsTabFragment() {
    }

    public static MovieReviewsTabFragment newInstance(long movieId) {
        MovieReviewsTabFragment f = new MovieReviewsTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        Log.d("ZZ", "Args " + movieId);
        return f;
    }


    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_review_tab, container, false);

        return v;
    }


    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);
        presenter.bindView(this);
        presenter.loadMovieReviews(getArguments().getLong(Args.ARG_MOVIE_ID));


        ArrayAdapter adapter = new ReviewsArrayAdapter(getActivity(), R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(null);

    }





    @Override
    public void displayReviews(List<Review> reviews) {
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        adapter.addAll(reviews);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(@StringRes int errorMessage) {
        // Do nothing, do not display the error message, just leave the empty list message
    }
}
