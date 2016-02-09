package com.talosdev.movies.ui;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.callbacks.MovieDetailsCallback;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.remote.FetchMovieDetailsTask;
import com.talosdev.movies.remote.json.Movie;

import java.text.SimpleDateFormat;

import hugo.weaving.DebugLog;

import static com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsCallback {


    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    private static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    public static final int ICON_FAV_SEL = R.drawable.ic_favorite_blue_24dp;
    public static final int ICON_FAV_UNSEL = R.drawable.ic_favorite_border_blue_24dp;

    private TextView titleView;
    private TextView descriptionView;
    private TextView releaseDateView;
    private TextView voteAverageView;
    private TextView voteCountView;
    private ImageView imageView;


    // holds the current movie being displayed
    private Movie currentMovie;
    // we need to store separately the id of the movie and the actual movie object
    // because the id comes is known on creation of the fragment, but the full details object
    // is loaded asynchronously
    private long currentMovieId;


    private boolean currentMovieIsFavorite;

    private MenuItem favItem;

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

        setHasOptionsMenu(true);

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
                    currentMovieId = argMovieId;
                    FetchMovieDetailsTask fetcher = new FetchMovieDetailsTask(this);
                    fetcher.execute(argMovieId);
                } else {
                    updateUI(currentMovie);
                    currentMovieId = currentMovie.id;
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_actions, menu);

        favItem = menu.findItem(R.id.menu_favorite);

        Cursor c1 = getActivity().getContentResolver().query(
                FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                null,
                null,
                null,
                null);
        setFavoriteActive(c1.moveToFirst());
    }

    /**
     * Depending on the boolean value marks/unmarks the current movie as favorite and
     * sets the correct icon.
     * @param active
     */
    private void setFavoriteActive(boolean active) {
        if (active) {
            favItem.setIcon(ICON_FAV_SEL);
            currentMovieIsFavorite = true;
        } else {
            favItem.setIcon(ICON_FAV_UNSEL);
            currentMovieIsFavorite = false;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                // currentMovie hasn't been loaded yet
                if (currentMovie == null) {
                    return false;
                }
                if (currentMovieIsFavorite) {
                    getActivity().getContentResolver().delete(
                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                            null,
                            null);
                    setFavoriteActive(false);
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(FavoriteMovieEntry.COLUMN_POSTER_PATH, currentMovie.posterPath);
                    getActivity().getContentResolver().insert(
                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                            cv);
                    setFavoriteActive(true);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
                return super.onOptionsItemSelected(item);
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
