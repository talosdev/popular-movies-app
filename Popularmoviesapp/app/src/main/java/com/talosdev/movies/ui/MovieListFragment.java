package com.talosdev.movies.ui;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.FetchPopularMoviesTask;
import com.talosdev.movies.remote.FetchPopularMoviesTask.FetchPopularMoviesParams;
import com.talosdev.movies.ui.activity.MovieDetailActivity;
import com.talosdev.movies.ui.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements AdapterView.OnItemClickListener, ActionBar.OnNavigationListener {

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";
    private static final String BUNDLE_CURRENT_CRITERION = "BUNDLE_KEY_CURRENT_CRITERION";
    public static final String SHARED_PREF_CRITERION = "SHARED_PREF_CRITERION";

    //   private static final String BUNDLE_CURRENT_PREVIOUS_CRITERION = "BUNDLE_KEY_CURRENT_PREVIOUS_CRITERION";

    private SortByCriterion currentSortBy;// = SortByCriterion.POPULARITY;
 //   private SortByCriterion previousSortBy;

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


        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        // TODO check this deprecation stuff
        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortOptions,
                android.R.layout.simple_spinner_dropdown_item);

        getActivity().getActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);

        if(savedInstanceState != null) {
            currentSortBy = (SortByCriterion) savedInstanceState.getSerializable(BUNDLE_CURRENT_CRITERION);
            //previousSortBy = (SortByCriterion) savedInstanceState.getSerializable(BUNDLE_CURRENT_PREVIOUS_CRITERION);
        }

        // If not found in bundle state, try shared preferences
        if (currentSortBy == null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String criterion = preferences.getString(SHARED_PREF_CRITERION, SortByCriterion.POPULARITY.toString());
            Log.d(Tags.PREF, String.format("Loaded criterion %s from shared preferences", criterion));
            currentSortBy = SortByCriterion.valueOf(criterion);
            //TODO I don't like that I have to notify like that
            getActivity().getActionBar().setSelectedNavigationItem(currentSortBy==SortByCriterion.POPULARITY?0:1);
        }


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
            Log.d(Tags.BUNDLE, "Trying to retrieve list from the saved instance state bundle");
            movies = (List<MoviePoster>) savedInstanceState.getSerializable(BUNDLE_MOVIE_POSTER);
            Log.d(Tags.BUNDLE, String.format("Found %d elements in the saved state bundle", movies.size()));
            page = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE, 0);
            Log.d(Tags.BUNDLE, String.format("Found current page: %d", page));
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

    /**
     * Note that this method is called not only when the user actually selects an option in the navbar,
     * but also after onCreate (eg after orientation changes). We don't want to take any action in
     * this second scenario. We do this by comparing the option that the itemPosition point to, to the
     * currently selected sorting option.
     * @param itemPosition
     * @param itemId
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d(Tags.NAVBAR, String.format("Navigation item selected: itemPosition=%d, itemId=%d", itemPosition, itemId));
        SortByCriterion newSortBy;
        switch (itemPosition) {
            case 0:
                newSortBy = SortByCriterion.POPULARITY;
                break;
            case 1:
                newSortBy = SortByCriterion.VOTE;
                break;
            default:
                Log.e(Tags.NAVBAR, String.format("Item position %d is not matched to any SortByCriterion"));
                return false;
        }
        // Only fetch new movies if the user has changed the selection
        boolean isUserAction = (newSortBy != currentSortBy);
        currentSortBy = newSortBy;
        if (isUserAction) {
            Log.d(Tags.NAVBAR, "Scrolling to top, because the criterion has changed");
            fetchMovies(1, true);
            // TODO check how this works with slow connections, maybe clear the adapter here?
            gridView.smoothScrollToPosition(0);
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String criterion = currentSortBy.toString();
        Log.d(Tags.PREF, String.format("Storing current criterion %s to shared preferences", criterion));
        editor.putString(SHARED_PREF_CRITERION, criterion);
        editor.commit();
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
            Log.i(TAG_SCROLL, String.format("Scroll listener will load more items, " +
                            "currently we are at page %d, with %d total items",
                    page, totalItemsCount));

            fetchMovies(page, false);
            return true;
        }
    }
}
