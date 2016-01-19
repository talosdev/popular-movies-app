package com.talosdev.movies.ui;


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

import com.talosdev.movies.R;
import com.talosdev.movies.constants.Intents;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.FetchPopularMoviesTask;
import com.talosdev.movies.remote.FetchPopularMoviesTask.FetchPopularMoviesParams;
import com.talosdev.movies.ui.activity.MainActivity;
import com.talosdev.movies.ui.activity.MovieDetailActivity;
import com.talosdev.movies.ui.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apapad on 19/11/15.
 */
public class MovieListFragment extends Fragment
        implements AdapterView.OnItemClickListener {


    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null
        GridView gridView = (GridView) inflater.inflate(R.layout.movie_list_fragment, container, false);;
        gridView.setOnItemClickListener(this);

        adapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item);
        gridView.setAdapter(adapter);

        gridView.setOnScrollListener(new MovieEndlessScrollListener());

        // TODO when rotating, do not fetch from API, but use the bundle instead

        FetchPopularMoviesTask fetchMovies = new FetchPopularMoviesTask(adapter);
        // TODO get this from SharedPreferences
        FetchPopularMoviesParams params =
                new FetchPopularMoviesParams(SortByCriterion.POPULARITY, 1);
        fetchMovies.execute(params);



        return gridView;
    }

    @Override
    public void onStart() {
        super.onStart();
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



    class MovieEndlessScrollListener extends EndlessScrollListener {

        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            Log.i(SCROLL_TAG, String.format("Scroll listener will load more items, " +
                            "currently we are at page %d, with %d total items",
                    page, totalItemsCount));

            FetchPopularMoviesTask fetchMovies = new FetchPopularMoviesTask(adapter);
            // TODO get this from SharedPreferences
            FetchPopularMoviesParams params =
                    new FetchPopularMoviesParams(SortByCriterion.POPULARITY, page);
            fetchMovies.execute(params);
            return true;
        }
    }
}
