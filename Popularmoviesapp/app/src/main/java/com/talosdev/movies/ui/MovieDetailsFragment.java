package com.talosdev.movies.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.callbacks.MovieDetailsCallback;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.remote.FetchMovieDetailsTask;
import com.talosdev.movies.remote.json.Movie;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;

import hugo.weaving.DebugLog;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsCallback {


    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    private static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";

    private TextView titleView;
    private TextView descriptionView;
    private TextView releaseDateView;
    private TextView voteAverageView;
    private TextView voteCountView;
    private ImageView imageView;

    // holds the current movie being displayed
    private Movie currentMovie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    public static MovieDetailsFragment newInstance(long movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle b = new Bundle();
        b.putLong(ARG_MOVIE_ID, movieId);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // persist the current movie
        if (currentMovie != null) {
            Log.d(Tags.BUNDLE, String.format("Storing current movie %d to bundle", currentMovie.id));
            outState.putParcelable(BUNDLE_MOVIE, Parcels.wrap(currentMovie));
        }
        super.onSaveInstanceState(outState);
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // container might be null when there is a configuration change from
        // two-pane to one-pane, and this fragment is reloaded, but without container
        if (container == null) {
            return null;
        }

        // Do not inflate the layout, if there are no arguments, for example
        // when the application is opened in two-pane mode, and so there is no
        // current movie selected to show in the details pane.
        if (getArguments() != null) {
            View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);
            titleView = (TextView) rootView.findViewById(R.id.movieTitle);
            descriptionView = (TextView) rootView.findViewById(R.id.movieDescription);
            releaseDateView = (TextView) rootView.findViewById(R.id.releaseDate);
            voteAverageView = (TextView) rootView.findViewById(R.id.vote_average);
            voteCountView = (TextView) rootView.findViewById(R.id.vote_count);
            imageView = (ImageView) rootView.findViewById(R.id.imageView);
            imageView.forceLayout();


            if (savedInstanceState != null) {
                currentMovie = Parcels.unwrap(savedInstanceState.getParcelable(BUNDLE_MOVIE));
                if (currentMovie != null) {
                    Log.d(Tags.BUNDLE, String.format("Restoring movie from bundle: %d", currentMovie.id));
                }
            }
            return rootView;
        } else {
            return null;
        }

    }

    @DebugLog
    @Override
    public void onStart() {
        super.onStart();
        // TODO
        // This is a hack for the problem I was facing with configuration changes from
        // two-pane landscape to one-pane portrait. If getView is null, we skip all
        // the logic of the fragment. For this to work correctly, the onCreateView method
        // must return a null view in the cases that this is happening.
        if (getView() != null) {
            if (getArguments() != null) {
                long argMovieId = getArguments().getLong(ARG_MOVIE_ID);
                if (currentMovie == null || argMovieId != currentMovie.id) {
                    FetchMovieDetailsTask fetcher = new FetchMovieDetailsTask(this);
                    fetcher.execute(argMovieId);
                } else {
                    updateUI(currentMovie);
                }
            }
        }
    }


    @Override
    public void onMovieDetailsReceived(Movie movie) {
        currentMovie = movie;

        updateUI(movie);
    }


    private void updateUI(Movie movie) {
        titleView.setText(movie.title);
        descriptionView.setText(movie.overview);

        if (movie.releaseDate != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String dateFormat = sharedPreferences.getString(
                    getResources().getString(R.string.pref_dateFormat_key),
                    getResources().getString(R.string.pref_dateFormat_value_a));
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

            releaseDateView.setText(sdf.format(movie.releaseDate));
        } else {
            releaseDateView.setText(getResources().getString(R.string.unavailable));
            releaseDateView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }

        voteAverageView.setText(movie.voteAverage + "");
        voteCountView.setText("based on " + movie.voteCount + " votes");

        final Movie m = movie;

        // Hack to make sure that the imageView will have finished being laied out
        // when we try to load the image into it, otherwise the width and height are 0
        // especially when reloading from the bundle (after a configuration change, or back button)
        imageView.post(new Runnable() {
            @Override
            public void run() {
                Picasso.
                        with(getActivity()).
                        load(TMDB.buildBackdropUrl(m.backdropPath)).
                        resize(imageView.getWidth(), imageView.getHeight()).
                        centerCrop().
                        into(imageView);
            }
        });
    }


    @DebugLog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
    }
}
