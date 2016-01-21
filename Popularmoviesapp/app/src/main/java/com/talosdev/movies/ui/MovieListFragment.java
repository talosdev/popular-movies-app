package com.talosdev.movies.ui;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SpinnerAdapter;

import com.talosdev.movies.R;
import com.talosdev.movies.constants.Intents;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.FetchPopularMoviesTask;
import com.talosdev.movies.remote.FetchPopularMoviesTask.FetchPopularMoviesParams;
import com.talosdev.movies.ui.activity.MovieDetailActivity;
import com.talosdev.movies.ui.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements AdapterView.OnItemClickListener, ActionBar.OnNavigationListener {

    public static final String TAG_BUNDLE = "BUNDLE";

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";
    private static final String BUNDLE_CURRENT_CRITERION = "BUNDLE_KEY_CURRENT_CRITERION";

    private SortByCriterion currentSortBy = SortByCriterion.POPULARITY;

    /**
     * The threshold required by {@link EndlessScrollListener}. Not really very important for UX.
     */
    private static final int SCROLL_THRESHOLD = 5;

    private ArrayAdapter adapter;
    private GridView gridView;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState != null) {
            currentSortBy = (SortByCriterion) savedInstanceState.getSerializable(BUNDLE_CURRENT_CRITERION);
        }

        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        // TODO check this deprecation stuff
        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortOptions,
                android.R.layout.simple_spinner_dropdown_item);

        getActivity().getActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null

        gridView = (GridView) inflater.inflate(R.layout.movie_list_fragment, container, false);
        gridView.setOnItemClickListener(this);

        List<MoviePoster> movies = new ArrayList<>();
        int page = 0;
        if (savedInstanceState != null) {
            Log.d(TAG_BUNDLE, "Trying to retrieve list from the saved instance state bundle");
            movies = (List<MoviePoster>) savedInstanceState.getSerializable(BUNDLE_MOVIE_POSTER);
            Log.d(TAG_BUNDLE, String.format("Found %d elements in the saved state bundle", movies.size()));
            page = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE, 0);
            Log.d(TAG_BUNDLE, String.format("Found current page: %d", page));
        }

        adapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item, movies);
        gridView.setAdapter(adapter);

        gridView.setOnScrollListener(new MovieEndlessScrollListener(SCROLL_THRESHOLD, page));

        // If nothing was found in the Bundle, fetch from the API
        if (movies.size() == 0) {
            fetchMovies(1, true);
        }
        return gridView;
    }

    /**
     *
     * @param page
     *  the page to request from the API (starting from index 1)
     * @param replace
     */
    private void fetchMovies(int page, boolean replace) {
        FetchPopularMoviesTask fetchMovies = new FetchPopularMoviesTask(adapter);
        FetchPopularMoviesParams params =
                new FetchPopularMoviesParams(currentSortBy, page, replace);
        fetchMovies.execute(params);
    }


    /**
     * Note that we need to save the current page that we are at, so that when loading back the bundle,
     * we can correctly initialize the {@link com.talosdev.movies.ui.MovieListFragment.MovieEndlessScrollListener}
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            int n = adapter.getCount();
            List<MoviePoster> movies = new ArrayList<>();
            for (int i=0; i<n; i++) {
                movies.add((MoviePoster) adapter.getItem(i));
            }
            outState.putSerializable(BUNDLE_MOVIE_POSTER, (ArrayList) movies);
            outState.putInt(BUNDLE_CURRENT_PAGE, movies.size() / TMDB.MOVIES_PER_PAGE - 1);
            outState.putSerializable(BUNDLE_CURRENT_CRITERION, currentSortBy);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long movieId = ((GridViewArrayAdapter) adapter).getItem(position).getMovieId();

        // The existence of the detail frame in the activity will tell us if we are on
        // mobile or on tablet
        View detailFrame = getActivity().findViewById(R.id.detail_frame);
        if (detailFrame != null) {
            // TABLET
            MovieDetailsFragment details = new MovieDetailsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setMovieId(movieId);
            ft.replace(R.id.detail_frame, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }  else {
            // MOBILE
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(Intents.EXTRA_MOVIE_ID, movieId);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d("NAV", String.format("Navigation item selected: itemPosition=%d, itemId=%d", itemPosition, itemId));
        switch (itemPosition) {
            case 0:
                currentSortBy = SortByCriterion.POPULARITY;
                break;
            case 1:
                currentSortBy = SortByCriterion.VOTE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Item position %d is not matched to any SortByCriterion"));
        }
        fetchMovies(1, true);
        gridView.smoothScrollToPosition(0);
        return false;
    }


    /**
     * Scroll listener that handles loading new elements when user is scrolling down.
     */
    class MovieEndlessScrollListener extends EndlessScrollListener {

        public MovieEndlessScrollListener(int visibleThreshold, int startPage) {
            super(visibleThreshold, startPage);
        }

        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            Log.i(SCROLL_TAG, String.format("Scroll listener will load more items, " +
                            "currently we are at page %d, with %d total items",
                    page, totalItemsCount));

            fetchMovies(page, false);
            return true;
        }
    }
}
