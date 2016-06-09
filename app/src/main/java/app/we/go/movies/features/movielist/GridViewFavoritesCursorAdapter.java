package app.we.go.movies.features.movielist;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import app.we.go.movies.R;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.db.DatabaseContract.FavoriteMoviesTable;
import app.we.go.movies.model.local.MoviePoster;
import app.we.go.movies.remote.URLBuilder;

/**
 * Created by apapad on 14/02/16.
 */
public class GridViewFavoritesCursorAdapter extends CursorAdapter {
    private final URLBuilder urlBuilder;
    private final int posterWidth;

    public GridViewFavoritesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        posterWidth = context.getResources().getDimensionPixelSize(R.dimen.poster_width_grid);
        urlBuilder = new URLBuilder();
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
        return new MoviePoster(c.getLong(FavoriteMoviesTable.COL_INDEX_MOVIE_ID), c.getString(FavoriteMoviesTable.COL_INDEX_POSTER_PATH));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
    }

    // TODO duplicate code
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String posterPath = cursor.getString(FavoriteMoviesTable.COL_INDEX_POSTER_PATH);

        if (posterPath != null) {
            String posterUrl = urlBuilder.buildPosterUrl(posterPath, posterWidth);
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