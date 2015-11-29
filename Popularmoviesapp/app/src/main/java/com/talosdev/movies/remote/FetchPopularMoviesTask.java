package com.talosdev.movies.remote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieList;

/**
 * Created by apapad on 13/11/15.
 */
public class FetchPopularMoviesTask extends AsyncTask<SortByCriterion, Void, List<Movie>> {


    private String LOG_TAG = "REMOTE";


    public FetchPopularMoviesTask() {
        super();

    }

    @Override
    protected List<Movie> doInBackground(SortByCriterion... params) {

        // Default sorting option is by popularity
        SortByCriterion sortBy = null;
        if (params.length == 0) {
            sortBy = SortByCriterion.POPULARITY;
        } else {
            sortBy = params[0];
        }


        try {

            PopularMoviesFetcher popularMoviesFetcher = new PopularMoviesFetcher();
            MovieList movieList = popularMoviesFetcher.fetch(sortBy);
            //TODO do something

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }

        return null;
    }




}
