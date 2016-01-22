package com.talosdev.movies.remote;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.remote.json.Movie;

import java.io.IOException;

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
        TextView voteView = (TextView) activity.findViewById(R.id.votes);
        ImageView imageView = (ImageView) activity.findViewById(R.id.imageView);

        titleView.setText(movie.title);
        descriptionView.setText(movie.overview);
        releaseDateView.setText(movie.releaseDate);
        voteView.setText(movie.voteAverage
                + " "
                + activity.getString(R.string.based_on)
                + " "
                + movie.voteCount + " "
                + activity.getString(R.string.votes)
        );


        Picasso.
                with(activity).
                load(TMDB.buildPosterUrl(movie.posterPath)).
                fit().
                into(imageView);
    }


}
