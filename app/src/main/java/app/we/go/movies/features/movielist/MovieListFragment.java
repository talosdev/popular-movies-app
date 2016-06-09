package app.we.go.movies.features.movielist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.we.go.framework.mvp.view.CacheablePresenterBasedFragment;
import app.we.go.movies.R;
import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.features.movielist.dependency.MovieListModule;
import app.we.go.movies.features.preferences.activity.PreferencesActivity;
import app.we.go.movies.model.local.MoviePoster;
import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.util.LOG;
import butterknife.Bind;
import hugo.weaving.DebugLog;

/**
 * Must be included in an activity that implements {@link MovieSelectedCallback}
 * <p/>
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends CacheablePresenterBasedFragment<MovieListContract.Presenter>
        implements MovieListContract.View, EndlessRecyclerView.Pager, MovieAdapter.MovieClickListener {

    private static final String BUNDLE_MOVIE_POSTER = "BUNDLE_KEY_MOVIE_POSTER";
    private static final String BUNDLE_CURRENT_PAGE = "BUNDLE_KEY_CURRENT_PAGE";


    private static final String SORT_BY = "app.we.go.SORT_BY";


    private static final int NUM_COLS = 3;


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
        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }


    @Override
    protected void injectDependencies(String presenterTag) {
        sortBy = SortByCriterion.byIndex(getArguments().getInt(SORT_BY));
        MovieApplication.get(getActivity()).getComponent().
                plus(new MovieListModule(getActivity(), sortBy, presenterTag)).inject(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);


        GridLayoutManager layoutManager = new GridLayoutManager(context, NUM_COLS);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
//        GridSpacingItemDecoration decorator = new GridSpacingItemDecoration(NUM_COLS, false);
//        decorator.setColWidth(context.getResources().getDimension(R.dimen.poster_width_grid));
//        recycler.addItemDecoration(decorator);

        adapter = new MovieAdapter(getContext(), this, urlBuilder);
        recycler.setAdapter(adapter);
        recycler.setPager(this);
        recycler.setThreshold(4);



        try {
            movieSelectedCallback = (MovieSelectedCallback) getActivity();
        } catch (Exception e) {
            LOG.e(Tags.UI, "This fragment must be used in an activity " +
                    "that implements " + MovieSelectedCallback.class);
        }

        List<MoviePoster> movies = new ArrayList<>();


    }


    @Override
    protected void initViewNoCache() {
        isLoading = true;
        presenter.loadMovies();
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

    @Override
    public boolean shouldLoad() {
        return !isLoading;
    }

    @Override
    public void loadNextPage() {
        isLoading = true;
        presenter.loadMovies();
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
