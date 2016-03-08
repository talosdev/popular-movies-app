package app.we.go.movies.ui.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.remote.json.Video;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoArrayAdapter extends ArrayAdapter {
    private final LayoutInflater inflater;
    private final int resource;
    private final List<Video> videos;

    public VideoArrayAdapter(Context context, int resource, List<Video> videos, LayoutInflater layoutInflater){
        super(context, resource, videos);
        this.inflater = layoutInflater;
        this.resource = resource;
        this.videos = videos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if(rowView == null){
            rowView = inflater.inflate(resource, parent, false);
            view = new ViewHolder();
            view.videoName = (TextView) rowView.findViewById(R.id.videoName);
            view.videoDetails = (TextView) rowView.findViewById(R.id.videoDetails);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        Video v = videos.get(position);
        view.videoName.setText(v.name);
        view.videoDetails.setText(v.type + " [" + v.site + "]");

        return rowView;

    }

    protected static class ViewHolder{
        protected TextView videoName;
        protected TextView videoDetails;
    }
}

