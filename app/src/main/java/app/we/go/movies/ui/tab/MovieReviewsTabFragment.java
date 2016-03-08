package app.we.go.movies.ui.tab;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.listener.MovieReviewsListener;
import app.we.go.movies.remote.ReviewsAsyncLoader;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.ui.ReviewsArrayAdapter;
import hugo.weaving.DebugLog;

/**
 * AsyncTaskLoader based solution. There are still various problems with loaders:
 * - Offscreen tabs in ViewPager won't have their loaders started. Known bug, a workaround exists.
 * - On configuration changes, onLoadFinished is called twice.
 * - Most important problem: In dual-pane mode, when the orientation changes, and then the user clicks
 * back, the loaders for the fragments in the backstack are no longer available, so network calls are made.
 *
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends ListFragment implements MovieReviewsListener, LoaderManager.LoaderCallbacks<List<Review>> {


    private static final String BUNDLE_KEY_REVIEWS = "app.we.go.movies.BUNDLE_REVIEWS";
    private static final int LOADER_REVIEWS = 2;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ReviewsArrayAdapter(getActivity(), R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(null);

        // We will restore state here: First check if the instance variable is set (eg back button)
        getLoaderManager().initLoader(LOADER_REVIEWS, null, this).startLoading();

        // Workaround for https://code.google.com/p/android/issues/detail?id=69586
        // Loader in off-screen tabs won't start if the first tab contains a loader.
        getView().post(new Runnable() {
            @Override
            public void run() {
                setUserVisibleHint(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
        return new ReviewsAsyncLoader(getActivity(),
                getArguments().getLong(Args.ARG_MOVIE_ID));    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
        if (data != null) {
            ((ArrayAdapter) getListAdapter()).clear();
            ((ArrayAdapter) getListAdapter()).addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
        ((ArrayAdapter) getListAdapter()).clear();
    }
}
