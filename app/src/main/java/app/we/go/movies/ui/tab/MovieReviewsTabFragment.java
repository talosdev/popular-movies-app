package app.we.go.movies.ui.tab;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.listener.MovieReviewsListener;
import app.we.go.movies.remote.FetchMovieReviewsTask;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.ui.ReviewsArrayAdapter;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends ListFragment implements MovieReviewsListener {


    private static final String BUNDLE_KEY_REVIEWS = "app.we.go.movies.BUNDLE_REVIEWS";
    private ArrayList<Review> currentReviews;

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_review_tab, container, false);

        return v;
    }

    public static MovieReviewsTabFragment newInstance(long movieId) {
        MovieReviewsTabFragment f = new MovieReviewsTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }

    // TODO why onActivityCreated?
    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ReviewsArrayAdapter(getActivity(), R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(null);

        // We will restore state here: First check if the instance variable is set (eg back button)
        // and if not, check the instance state bundle (eg orientation change).
        if (currentReviews != null) {
            onMovieReviewsReceived(currentReviews);
        } else if (savedInstanceState != null) {
            ArrayList<Review> reviewsFromBundle = savedInstanceState.<Review>getParcelableArrayList(BUNDLE_KEY_REVIEWS);
            if (reviewsFromBundle != null) {
                onMovieReviewsReceived(reviewsFromBundle);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO should this be here?
        if (getView() != null) {
            if (currentReviews == null) {
                FetchMovieReviewsTask task = new FetchMovieReviewsTask(this);
                task.execute(getArguments().getLong(Args.ARG_MOVIE_ID));
            }
        }
    }


    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (currentReviews != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_REVIEWS, currentReviews);
        }
    }


    @Override
    public void onMovieReviewsReceived(ArrayList<Review> reviews) {
        currentReviews = reviews;
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        adapter.addAll(reviews);
        adapter.notifyDataSetChanged();
    }
}
