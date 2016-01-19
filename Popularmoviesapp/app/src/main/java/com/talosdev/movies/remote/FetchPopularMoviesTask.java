package com.talosdev.movies.remote;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieList;
import com.talosdev.movies.ui.GridViewArrayAdapter;

/**
 * Created by apapad on 13/11/15.
 */
public class FetchPopularMoviesTask extends AsyncTask<SortByCriterion, Void, MovieList> {


    private final ArrayAdapter adapter;
    private String LOG_TAG = "REMOTE";


    public FetchPopularMoviesTask(ArrayAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    protected MovieList doInBackground(SortByCriterion... params) {

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
            return movieList;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }

    }


    @Override
    protected void onPostExecute(MovieList movieList) {
        super.onPostExecute(movieList);
        adapter.addAll(getPosterURLs(movieList));
        Log.d("UI", "Received posterList with " + movieList.movies.size() + " items. Notifying the adapter...");
        adapter.notifyDataSetChanged();

    }

    private List<MoviePoster> getPosterURLs(MovieList movieList) {
        List<MoviePoster> urls = new ArrayList<>(movieList.movies.size());
        for (Movie movie:movieList.movies) {
            String poster = movie.posterPath;

            urls.add(new MoviePoster(movie.id, TMDB.buildPosterUrl(poster)));
        }
        return urls;
    }
}
