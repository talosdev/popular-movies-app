package com.talosdev.movies.remote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.remote.json.Movie;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by apapad on 13/11/15.
 */
public class FetchMovieDetailsTask extends AsyncTask<Long, Void, Movie> {


    private final Activity activity;


    public FetchMovieDetailsTask(Activity activity) {
        super();
        this.activity = activity;

    }

    @Override
    protected Movie doInBackground(Long... params) {


        try {

            MovieDetailsFetcher moviesFetcher = new MovieDetailsFetcher();
            Movie movie = moviesFetcher.fetch(params[0]);

            return movie;
        } catch (IOException e) {
            Log.e(Tags.REMOTE, "Error when contacting the server to get the movie details", e);
            return null;
        }

    }


    // TODO check if this thing that I am doing with casting the activity to activity is the best way.
    @Override
    protected void onPostExecute(Movie movie) {
        if (movie == null) {
            return;
        }

        TextView titleView = (TextView) activity.findViewById(R.id.movieTitle);
        TextView descriptionView = (TextView) activity.findViewById(R.id.movieDescription);
        TextView releaseDateView = (TextView) activity.findViewById(R.id.releaseDate);
        TextView voteAverageView = (TextView) activity.findViewById(R.id.vote_average);
        TextView voteCountView = (TextView) activity.findViewById(R.id.vote_count);
        ImageView imageView = (ImageView) activity.findViewById(R.id.imageView);

        titleView.setText(movie.title);
        descriptionView.setText(movie.overview);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String dateFormat = sharedPreferences.getString(
                activity.getResources().getString(R.string.pref_dateFormat_key),
                activity.getResources().getString(R.string.pref_dateFormat_value_a));
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        releaseDateView.setText(sdf.format(movie.releaseDate));
        voteAverageView.setText(movie.voteAverage + "");
        voteCountView.setText("based on " + movie.voteCount + " votes");


        Picasso.
                with(activity).
                load(TMDB.buildBackdropUrl(movie.backdropPath)).
                resize(imageView.getWidth(), imageView.getHeight()).
                centerCrop().
                into(imageView);
    }


}
