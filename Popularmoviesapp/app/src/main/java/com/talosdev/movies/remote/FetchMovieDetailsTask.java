package com.talosdev.movies.remote;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.remote.json.Movie;

import java.io.IOException;

/**
 * Created by apapad on 13/11/15.
 */
public class FetchMovieDetailsTask extends AsyncTask<String, Void, Movie> {


    private final Context context;
    private String LOG_TAG = "REMOTE";


    public FetchMovieDetailsTask(Context context) {
        super();
        this.context = context;

    }

    @Override
    protected Movie doInBackground(String... params) {


        try {

            MovieDetailsFetcher moviesFetcher = new MovieDetailsFetcher();
            Movie movie = moviesFetcher.fetch(params[0]);

            return movie;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }

    }


    // TODO check if this thing that I am doing with casting the context to activity is the best way.
    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        TextView titleView = (TextView) ((Activity) context).findViewById(R.id.movieTitle);
        TextView descriptionView = (TextView) ((Activity) context).findViewById(R.id.movieDescription);
        TextView releaseDateView = (TextView) ((Activity) context).findViewById(R.id.releaseDate);
        TextView voteView = (TextView) ((Activity) context).findViewById(R.id.votes);
        ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.imageView);

        titleView.setText(movie.title);
        descriptionView.setText(movie.overview);
        releaseDateView.setText(movie.releaseDate);
        voteView.setText(movie.voteAverage
                + " "
                + context.getString(R.string.based_on)
                + " "
                + movie.voteCount + " "
                + context.getString(R.string.votes)
        );


        Picasso.
                with(context).
                load(TMDB.buildPosterUrl(movie.posterPath)).
                fit().
                into(imageView);
    }
}
