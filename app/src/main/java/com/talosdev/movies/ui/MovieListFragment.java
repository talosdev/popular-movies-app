package com.talosdev.movies.ui;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.SpinnerAdapter;

import com.talosdev.movies.R;
import com.talosdev.movies.callbacks.MovieSelectedCallback;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.FetchPopularMoviesTask;
import com.talosdev.movies.remote.FetchPopularMoviesTask.FetchPopularMoviesParams;
import com.talosdev.movies.ui.activity.PreferencesActivity;
import com.talosdev.movies.ui.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements AdapterView.OnItemClickListener, ActionBar.OnNavigationListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";

    // Currently selected sort by criterion is stored in SharedPReferences and not in the bundle
    // so that is can persist across app shutdowns and phone reboots etc
    public static final String SHARED_PREF_CRITERION = "SHARED_PREF_CRITERION";
    public static final int LOADER_FAVORITES = 0;

    private SortByCriterion currentSortBy;

    /**
     * The threshold required by {@link EndlessScrollListener}. Not really very important for UX.
     */
    private static final int SCROLL_THRESHOLD = 5;

    private ArrayAdapter tmdbAdapter;
    private CursorAdapter favoritesAdapter;

    private GridView gridView;

    @DebugLog
    @Override
    public void onStart() {
        super.onStart();
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        // TODO check this deprecation stuff
        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortOptions,
                android.R.layout.simple_spinner_dropdown_item);

        getActivity().getActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);

        if (currentSortBy == null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String criterion = preferences.getString(SHARED_PREF_CRITERION, SortByCriterion.POPULARITY.toString());
            Log.d(Tags.PREF, String.format("Loaded criterion %s from shared preferences", criterion));
            currentSortBy = SortByCriterion.valueOf(criterion);
            //TODO find a better way to get the index
            getActivity().getActionBar().setSelectedNavigationItem(currentSortBy.getIndex());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_FAVORITES, null, this);
    }

    @DebugLog
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

        tmdbAdapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item, movies);
        favoritesAdapter = new GridViewFavoritesCursorAdapter(getActivity(), null, 0);

        if (currentSortBy == SortByCriterion.FAVORITES) {
            gridView.setAdapter(favoritesAdapter);
        } else {
            gridView.setAdapter(tmdbAdapter);
        }

        gridView.setOnScrollListener(new MovieEndlessScrollListener(SCROLL_THRESHOLD, page));

        // If nothing was found in the Bundle, fetch from the API
        if (movies.size() == 0) {
            fetchMovies(1, true);
        }
        return gridView;
    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * @param page    the page to request from the API (starting from index 1)
     * @param replace
     */
    private void fetchMovies(int page, boolean replace) {
        FetchPopularMoviesTask fetchMovies = new FetchPopularMoviesTask(tmdbAdapter);
        FetchPopularMoviesParams params =
                new FetchPopularMoviesParams(currentSortBy, page, replace);
        fetchMovies.execute(params);
    }


    /**
     * Note that we need to save the current page that we are at, so that when loading back the bundle,
     * we can correctly initialize the {@link com.talosdev.movies.ui.MovieListFragment.MovieEndlessScrollListener}
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO generalize this so that it uses getAdapter()
        if (tmdbAdapter != null) {
            int n = tmdbAdapter.getCount();
            List<MoviePoster> movies = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                movies.add((MoviePoster) tmdbAdapter.getItem(i));
            }
            outState.putSerializable(BUNDLE_MOVIE_POSTER, (ArrayList) movies);
            outState.putInt(BUNDLE_CURRENT_PAGE, movies.size() / TMDB.MOVIES_PER_PAGE - 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long movieId = ((MoviePoster) gridView.getAdapter().getItem(position)).getMovieId();

        ((MovieSelectedCallback) getActivity()).onMovieSelected(movieId);

    }

    /**
     * Note that this method is called not only when the user actually selects an option in the navbar,
     * but also after onCreate (eg after orientation changes). We don't want to take any action in
     * this second scenario. We do this by comparing the option that the itemPosition point to, to the
     * currently selected sorting option.
     *
     * @param itemPosition
     * @param itemId
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d(Tags.NAVBAR, String.format("Navigation item selected: itemPosition=%d, itemId=%d", itemPosition, itemId));
        SortByCriterion newSortBy = SortByCriterion.byIndex(itemPosition);
        if (newSortBy == null) {
            Log.e(Tags.NAVBAR, String.format("Item position %d is not matched to any SortByCriterion"));
            return false;
        }
        // Only fetch new movies if the user has changed the selection
        boolean isUserAction = (newSortBy != currentSortBy);
        currentSortBy = newSortBy;
        if (isUserAction) {
            if (currentSortBy == SortByCriterion.FAVORITES) {
                gridView.smoothScrollToPosition(0);
                gridView.setAdapter(favoritesAdapter);
            } else {
                Log.d(Tags.NAVBAR, "Scrolling to top, because the criterion has changed");
                fetchMovies(1, true);
                gridView.setAdapter(tmdbAdapter);

                // TODO check how this works with slow connections, maybe clear the tmdbAdapter here?
                gridView.smoothScrollToPosition(0);
            }

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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(getActivity(), PreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        if (id == LOADER_FAVORITES) {

            String[] projection = new String[]{
                    FavoriteMovieEntry._ID,
                    FavoriteMovieEntry.COLUMN_MOVIE_ID,
                    FavoriteMovieEntry.COLUMN_POSTER_PATH
            };

            CursorLoader loader = new CursorLoader(getActivity(),
                    FavoriteMovieEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    "ORDER BY " + FavoriteMovieEntry._ID + " DESC ");

            return loader;
        } else {
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        favoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        favoritesAdapter.swapCursor(null);
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
