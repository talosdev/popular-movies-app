package com.talosdev.movies.ui;

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

import com.talosdev.movies.data.MoviePoster;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // TODO use the field?
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }


        Picasso.
                with(context).
                load(getItem(position).getPosterUrl()).
                fit().
                into((ImageView) convertView);

        return convertView;
    }
}