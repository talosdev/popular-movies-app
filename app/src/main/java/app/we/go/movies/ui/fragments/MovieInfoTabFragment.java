package app.we.go.movies.ui.fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import app.we.go.movies.R;
import app.we.go.movies.callbacks.MovieDetailsCallback;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.ui.tab.MovieDetailsPagerAdapter;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieInfoTabFragment extends Fragment implements MovieDetailsCallback {

    private static MovieDetailsPagerAdapter adapter;
    private TextView descriptionView;
    private TextView releaseDateView;
    private TextView voteAverageView;
    private TextView voteCountView;

    private Movie currentMovie;

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





    public static MovieInfoTabFragment newInstance(MovieDetailsPagerAdapter movieDetailsPagerAdapter) {
        adapter = movieDetailsPagerAdapter;

        MovieInfoTabFragment f = new MovieInfoTabFragment();

        return f;
    }


    @DebugLog
    @Override
    public void onResume() {
        super.onResume();

        Movie m = adapter.getCurrentMovie();
        if (m != null) {
            updateUI(m);
        }
    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
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
        voteCountView.setText("based on " + movie.voteCount + " votes");


    }

    @Override
    public void onMovieDetailsReceived(Movie movie) {
        currentMovie = movie;
        updateUI(movie);
    }
}
