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

/**
 * Created by apapad on 13/11/15.
 */
public class FetchPopularMoviesTask extends
        AsyncTask<FetchPopularMoviesTask.FetchPopularMoviesParams, Void, FetchPopularMoviesTask.FetchPopularMoviesResult> {


    private final ArrayAdapter         adapter;
    private String LOG_TAG = "REMOTE";


    public FetchPopularMoviesTask(ArrayAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    protected FetchPopularMoviesResult doInBackground(FetchPopularMoviesParams... params) {

        // Default sorting option is by popularity
        SortByCriterion sortBy = null;
        if (params.length == 0) {
            sortBy = SortByCriterion.POPULARITY;
        } else {
            sortBy = params[0].getSortBy();
        }

        int page = params[0].getPage();


        try {

            PopularMoviesFetcher popularMoviesFetcher = new PopularMoviesFetcher();
            MovieList movieList = popularMoviesFetcher.fetch(sortBy, page);
            FetchPopularMoviesResult result = new FetchPopularMoviesResult(movieList, params[0].isReplace());

            return result;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }

    }


    @Override
    protected void onPostExecute(FetchPopularMoviesResult result) {
        super.onPostExecute(result);
        if (result.isReplace()) {
            adapter.clear();
        }
        adapter.addAll(getPosterURLs(result.getMovieList()));
        Log.d("UI", "Received posterList with " + result.getMovieList().movies.size() + " items. Notifying the adapter...");
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


    public static class FetchPopularMoviesParams {
        private SortByCriterion sortBy;
        private int page;
        private boolean replace;

        public FetchPopularMoviesParams(SortByCriterion sortBy, int page, boolean replace) {
            this.sortBy = sortBy;
            this.page = page;
            this.replace = replace;
        }

        public SortByCriterion getSortBy() {
            return sortBy;
        }

        public int getPage() {
            return page;
        }

        public boolean isReplace() {
            return replace;
        }
    }

    public static class FetchPopularMoviesResult {
        private MovieList movieList;
        private boolean replace;

        public FetchPopularMoviesResult(MovieList movieList, boolean replace) {
            this.movieList = movieList;
            this.replace = replace;
        }

        public MovieList getMovieList() {
            return movieList;
        }

        public boolean isReplace() {
            return replace;
        }
    }
}
