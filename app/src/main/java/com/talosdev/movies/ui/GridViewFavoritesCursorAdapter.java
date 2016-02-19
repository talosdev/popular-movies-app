package com.talosdev.movies.ui;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.constants.Tags;
import com.talosdev.movies.contract.MoviesContract.FavoriteMovieEntry;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.remote.URLBuilder;

/**
 * Created by apapad on 14/02/16.
 */
public class GridViewFavoritesCursorAdapter extends CursorAdapter {
    private final URLBuilder urlBuilder;

    public GridViewFavoritesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        urlBuilder = new URLBuilder(context);
    }

    /**
     * We need to override this and return a {@link MoviePoster} object instead of a Cursor,
     * so that the behaviour matches the {@link GridViewArrayAdapter}'s behaviour.
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        Cursor c = (Cursor) super.getItem(position);
        return new MoviePoster(c.getLong(FavoriteMovieEntry.COL_INDEX_MOVIE_ID), c.getString(FavoriteMovieEntry.COL_INDEX_POSTER_PATH));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
    }

    // TODO duplicate code
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String posterPath = cursor.getString(FavoriteMovieEntry.COL_INDEX_POSTER_PATH);

        if (posterPath != null) {
            String posterUrl = urlBuilder.buildPosterUrl(posterPath);
            Log.v(Tags.REMOTE, String.format("Requesting poster image: %s", posterUrl));

            Picasso.
                    with(context).
                    load(posterUrl).
                    placeholder(R.drawable.background).
                    // TODO
                            error(R.drawable.error128).
                    resizeDimen(R.dimen.poster_width_grid, R.dimen.poster_height_grid).
                    centerCrop().
                    into((ImageView) view);
        } else {
            Picasso.
                    with(context).
                    //TODO resolution
                            load(R.drawable.movie128).
                    into((ImageView) view);
        }

    }


}
