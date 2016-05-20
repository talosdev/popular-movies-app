package app.we.go.movies.movielist;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.SortByChangedCallback;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;
import app.we.go.movies.data.MoviePoster;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.listener.MovieSelectedCallback;
import app.we.go.movies.remote.FetchPopularMoviesTask;
import app.we.go.movies.remote.FetchPopularMoviesTask.FetchPopularMoviesParams;
import app.we.go.movies.ui.activity.PreferencesActivity;
import app.we.go.movies.ui.util.EndlessScrollListener;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";


    public static final int LOADER_FAVORITES = 0;
    private static final String SORT_BY = "app.we.go.SORT_BY";

    /**
     * The threshold required by {@link EndlessScrollListener}. Not really very important for UX.
     */
    private static final int SCROLL_THRESHOLD = 5;

    private ArrayAdapter tmdbAdapter;
    private CursorAdapter favoritesAdapter;

    private GridView gridView;
    private EndlessScrollListener onScrollListener;
    private SortByChangedCallback sortByChangedCallback;


    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(SortByCriterion sortBy) {
        MovieListFragment f = new MovieListFragment();
        Bundle b = new Bundle();
        b.putInt(SORT_BY, sortBy.getIndex());
        f.setArguments(b);
        return f;
    }

    @DebugLog
    @Override
    public void onStart() {
        super.onStart();
    }

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sortByChangedCallback = (SortByChangedCallback) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<MoviePoster> movies = new ArrayList<>();
        int page = 0;
        if (savedInstanceState != null) {
            Log.d(Tags.BUNDLE, "Trying to retrieve list from the saved instance state bundle");
            movies = (List<MoviePoster>) savedInstanceState.getSerializable(BUNDLE_MOVIE_POSTER);
            Log.d(Tags.BUNDLE, String.format("Found %d elements in the saved state bundle", movies.size()));
            page = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE, 0);
            Log.d(Tags.BUNDLE, String.format("Found current page: %d", page));
        }

        SortByCriterion sortBy = SortByCriterion.byIndex(getArguments().getInt(SORT_BY));

        sortByChangedCallback.sortByChanged(sortBy);

        tmdbAdapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item, movies);
        favoritesAdapter = new GridViewFavoritesCursorAdapter(getActivity(), null, 0);
        onScrollListener = new MovieEndlessScrollListener(sortBy, SCROLL_THRESHOLD, page);


        switch(sortBy) {

            case POPULARITY:
            case VOTE:
                gridView.setAdapter(tmdbAdapter);
                gridView.setOnScrollListener(onScrollListener);
                // If nothing was found in the Bundle, fetch from the API
                if (movies.size() == 0) {
                    fetchMovies(sortBy, 1, true);
                }
                break;
            case FAVORITES:
                gridView.setAdapter(favoritesAdapter);
                gridView.setOnScrollListener(null);
                getLoaderManager().initLoader(LOADER_FAVORITES, null, this);
                break;
        }



    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null
        super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);


        gridView = (GridView) inflater.inflate(R.layout.movie_list_fragment, container, false);
        gridView.setOnItemClickListener(this);





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
    private void fetchMovies(SortByCriterion sortBy, int page, boolean replace) {
        FetchPopularMoviesTask fetchMovies = new FetchPopularMoviesTask(tmdbAdapter);
        FetchPopularMoviesParams params =
                new FetchPopularMoviesParams(sortBy, page, replace);
        fetchMovies.execute(params);
    }


    /**
     * Note that we need to save the current page that we are at, so that when loading back the bundle,
     * we can correctly initialize the {@link MovieListFragment.MovieEndlessScrollListener}
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
        MoviePoster moviePoster = (MoviePoster) gridView.getAdapter().getItem(position);


        ((MovieSelectedCallback) getActivity()).onMovieSelected(moviePoster.getMovieId(), moviePoster.getPosterPath());

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


            CursorLoader loader = new CursorLoader(getActivity(),
                    FavoriteMovieEntry.CONTENT_URI,
                    FavoriteMovieEntry.PROJECTION,
                    null,
                    null,
                    FavoriteMovieEntry._ID + " DESC ");

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

        private SortByCriterion sortBy;

        public MovieEndlessScrollListener(SortByCriterion sortBy, int visibleThreshold, int startPage) {
            super(visibleThreshold, startPage);
            this.sortBy = sortBy;
        }

        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            Log.i(TAG_SCROLL, String.format("Scroll listener will load more items, " +
                            "currently we are at page %d, with %d total items",
                    page, totalItemsCount));
            fetchMovies(sortBy, page, false);
            return true;
        }
    }


}
