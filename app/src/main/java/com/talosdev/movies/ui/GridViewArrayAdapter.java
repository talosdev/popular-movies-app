package com.talosdev.movies.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.talosdev.movies.R;
import com.talosdev.movies.data.MoviePoster;

import java.util.List;

/**
 * Created by apapad on 19/11/15.
 */
public class GridViewArrayAdapter extends ArrayAdapter<MoviePoster> {

    private final Context context;
    private final LayoutInflater inflater;
    private final int resource;


    public GridViewArrayAdapter(Context context, int resource, List<MoviePoster> movies) {
        super(context, resource, movies);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resource = resource;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // TODO use the field?
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        String posterUrl = getItem(position).getPosterUrl();
        if (posterUrl != null) {
            Picasso.
                    with(context).
                    load(posterUrl).
                    placeholder(R.drawable.background).
                    error(R.drawable.error128).
                    resizeDimen(R.dimen.poster_width_grid, R.dimen.poster_height_grid).
                    centerCrop().
                    into((ImageView) convertView);
        } else {
            Picasso.
                    with(context).
                    load(R.drawable.movie128).
                    into((ImageView) convertView);
        }


        return convertView;
    }
}
