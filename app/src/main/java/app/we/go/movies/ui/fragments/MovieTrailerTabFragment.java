package app.we.go.movies.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.we.go.movies.R;
import app.we.go.movies.callbacks.MovieDetailsCallback;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.ui.tab.MovieDetailsPagerAdapter;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieTrailerTabFragment extends Fragment implements MovieDetailsCallback {


    private static MovieDetailsPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_trailer_tab, container, false);
        return v;
    }

    public static MovieTrailerTabFragment newInstance(MovieDetailsPagerAdapter movieDetailsPagerAdapter) {
        adapter = movieDetailsPagerAdapter;
        MovieTrailerTabFragment f = new MovieTrailerTabFragment();

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public void onMovieDetailsReceived(Movie movie) {

    }
}
