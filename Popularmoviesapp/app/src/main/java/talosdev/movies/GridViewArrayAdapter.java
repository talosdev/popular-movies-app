package talosdev.movies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apapad on 19/11/15.
 */
public class GridViewArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<String> objects;
    private final int resource;


    public GridViewArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        
        Picasso.
                with(context).
                load(objects.get(position)).
                fit().
                into((ImageView) convertView);

        return convertView;
    }
}
