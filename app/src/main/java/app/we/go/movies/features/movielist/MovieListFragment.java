package app.we.go.movies.features.movielist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.model.local.MoviePoster;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.features.movielist.dependency.HasMovieListComponent;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.features.preferences.activity.PreferencesActivity;
import app.we.go.movies.util.LOG;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Must be included in an activity that implements {@link MovieSelectedCallback}
 * <p/>
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements  MovieListContract.View, EndlessRecyclerView.Pager, MovieAdapter.MovieClickListener {

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";


    private static final String SORT_BY = "app.we.go.SORT_BY";


    private static final int NUM_COLS = 3;

    private ArrayAdapter tmdbAdapter;
    private CursorAdapter favoritesAdapter;

//    private SortByChangedCallback sortByChangedCallback;

    private MovieSelectedCallback movieSelectedCallback;

    @Inject
    MovieListContract.Presenter presenter;

    @Inject
    Context context;

    @Inject
    URLBuilder urlBuilder;

    @Bind(R.id.movie_recycler_view)
    EndlessRecyclerView recycler;


    private boolean isLoading;
    private SortByCriterion sortBy;
    private MovieAdapter adapter;


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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        ((HasMovieListComponent) getActivity()).getComponent().inject(this);

        presenter.bindView(this);

        GridLayoutManager layoutManager = new GridLayoutManager(context, NUM_COLS);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
//        GridSpacingItemDecoration decorator = new GridSpacingItemDecoration(NUM_COLS, false);
//        decorator.setColWidth(context.getResources().getDimension(R.dimen.poster_width_grid));
//        recycler.addItemDecoration(decorator);

        adapter = new MovieAdapter(getContext(), this, urlBuilder);
        recycler.setAdapter(adapter);
        recycler.setPager(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        sortByChangedCallback = (SortByChangedCallback) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            movieSelectedCallback = (MovieSelectedCallback) getActivity();
        } catch (Exception e) {
            LOG.e(Tags.UI, "This fragment must be used in an activity " +
                    "that implements " + MovieSelectedCallback.class);
        }

        List<MoviePoster> movies = new ArrayList<>();
        int page = 0;
        if (savedInstanceState != null) {
            Log.d(Tags.BUNDLE, "Trying to retrieve list from the saved instance state bundle");
            movies = (List<MoviePoster>) savedInstanceState.getSerializable(BUNDLE_MOVIE_POSTER);
            Log.d(Tags.BUNDLE, String.format("Found %d elements in the saved state bundle", movies.size()));
            page = savedInstanceState.getInt(BUNDLE_CURRENT_PAGE, 0);
            Log.d(Tags.BUNDLE, String.format("Found current page: %d", page));
        }

        sortBy = SortByCriterion.byIndex(getArguments().getInt(SORT_BY));

//        sortByChangedCallback.sortByChanged(sortBy);

        tmdbAdapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item, movies);
        favoritesAdapter = new GridViewFavoritesCursorAdapter(getActivity(), null, 0);


        switch (sortBy) {

            case POPULARITY:
            case VOTE:

                // If nothing was found in the Bundle, fetch from the API
                if (movies.size() == 0) {
                    presenter.loadMovies(sortBy);
                }
                break;
            case FAVORITES:

                break;
        }


    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
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
    public void showMovieList(List<Movie> movies) {
        isLoading = false;

        adapter.addMovies(movies);
    }

    @Override
    public void navigateToMovieDetails(Movie movie) {
        movieSelectedCallback.onMovieSelected(movie);
    }


    public void showError(Context context, String logMessage, int resourceId, @Nullable Throwable t) {
        LOG.e(Tags.REMOTE, logMessage, t);
        LOG.e(Tags.REMOTE, "null: " + (t==null));
        LOG.e(Tags.REMOTE, t.getClass().getSimpleName());
        LOG.e(Tags.REMOTE, t.getMessage());

        Toast.makeText(this.context, resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean shouldLoad() {
        return !isLoading;
    }

    @Override
    public void loadNextPage() {
        isLoading = true;
        presenter.loadMovies(sortBy);
    }

    @Override
    public void onPosterClick(Movie movie) {
        presenter.openMovieDetails(movie);
    }

    /**
     * Callback used for fragment-enclosing activity interaction
     * when user selects a movie in the list.
     * Created by apapad on 28/01/16.
     */
    public interface MovieSelectedCallback {
        void onMovieSelected(Movie movie);
    }
}
