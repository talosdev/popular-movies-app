package com.talosdev.movies.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talosdev.movies.R;
import com.talosdev.movies.remote.FetchMovieDetailsTask;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment {

    private String movieId;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selected", movieId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            movieId = savedInstanceState.getString("selected");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        FetchMovieDetailsTask fetcher = new FetchMovieDetailsTask(view.getContext());
        fetcher.execute(movieId);
    }

}
