package app.we.go.movies.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.data.MoviePoster;
import app.we.go.movies.remote.URLBuilder;

/**
 * Created by apapad on 19/11/15.
 */
public class GridViewArrayAdapter extends ArrayAdapter<MoviePoster> {

    private final Context context;
    private final int resource;
    private final URLBuilder urlBuilder;


    public GridViewArrayAdapter(Context context, int resource, List<MoviePoster> movies) {
        super(context, resource, movies);
        this.context = context;
        this.resource = resource;
        urlBuilder = new URLBuilder();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            if (context instanceof Activity) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(resource, parent, false);
            }
        }

        String posterPath = getItem(position).getPosterPath();
        if (posterPath != null) {
            // already in pixels
            int posterWidth = context.getResources().getDimensionPixelSize(R.dimen.poster_width_grid);
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
                    into((ImageView) convertView);
        } else {
            Picasso.
                    with(context).
                    //TODO resolution
                    load(R.drawable.movie128).
                    into((ImageView) convertView);
        }


        return convertView;
    }
}
