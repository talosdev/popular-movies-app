package app.we.go.movies.ui.tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.listener.MovieInfoListener;
import app.we.go.movies.remote.MovieDetailsFetcher;
import app.we.go.movies.remote.MovieInfoLoader;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.ui.MovieApplication;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieInfoTabFragment extends Fragment implements MovieInfoListener, LoaderManager.LoaderCallbacks<Movie> {

    private TextView descriptionView;
    private TextView releaseDateView;
    private TextView voteAverageView;
    private TextView voteCountView;

    private Movie currentMovie;

    @Inject
    MovieDetailsFetcher fetcher;

    public static MovieInfoTabFragment newInstance(long movieId) {
        MovieInfoTabFragment f = new MovieInfoTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO is this required?
        if (savedInstanceState != null) {
            currentMovie = savedInstanceState.getParcelable(Movie.BUNDLE_KEY);
        }
    }


    @DebugLog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MovieApplication) context.getApplicationContext()).getComponent().inject(this);
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

        View v = inflater.inflate(R.layout.movie_details_info_tab, container, false);

        descriptionView = (TextView) v.findViewById(R.id.movieDescription);
        if (descriptionView==null) {
            Log.e("ERROR", "Bummer");
        }
        releaseDateView = (TextView) v.findViewById(R.id.releaseDate);
        voteAverageView = (TextView) v.findViewById(R.id.vote_average);
        voteCountView = (TextView) v.findViewById(R.id.vote_count);

        return v;
    }

    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(1, null, this);
    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Movie.BUNDLE_KEY, currentMovie);
    }


    private void updateUI(Movie movie) {
        if (getView() == null) {
            return;
        }

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
        voteCountView.setText("(" + movie.voteCount + " votes)");


    }

    @Override
    public void onMovieInfoReceived(Movie movie) {
        currentMovie = movie;
        updateUI(movie);
        ((MovieInfoListener) getParentFragment()).onMovieInfoReceived(movie);
    }


    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new MovieInfoLoader(getActivity(), fetcher, getArguments().getLong(Args.ARG_MOVIE_ID));
    }

    @DebugLog
    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data != null) {
            onMovieInfoReceived(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }
}
