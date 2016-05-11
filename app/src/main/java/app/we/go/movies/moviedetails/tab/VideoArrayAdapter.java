package app.we.go.movies.moviedetails.tab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.json.Video;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoArrayAdapter extends ArrayAdapter {
    private final LayoutInflater inflater;
    private final int resource;
    private final List<Video> videos;
    // TODO inject
    private final URLBuilder urlBuilder;

    @OnClick(R.id.share_button)
    public void onShareButton() {

    }


    public VideoArrayAdapter(Context context, int resource, List<Video> videos, LayoutInflater layoutInflater){
        super(context, resource, videos);
        this.inflater = layoutInflater;
        this.resource = resource;
        this.videos = videos;
        this.urlBuilder = new URLBuilder();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if(rowView == null){
            rowView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder(rowView, getContext(), urlBuilder);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Video v = videos.get(position);
        viewHolder.videoName.setText(v.name);
        viewHolder.videoDetails.setText(v.type + " [" + v.site + "]");
        viewHolder.videoKey = v.key;

        return rowView;

    }

    protected static class ViewHolder{
        @Bind(R.id.videoName)
        TextView videoName;

        @Bind(R.id.videoDetails)
        TextView videoDetails;

        String videoKey;
        private Context context;
        private URLBuilder urlBuilder;

        // TODO ugly dependency to context
        public ViewHolder(View v, Context context, URLBuilder urlBuilder) {
            this.context = context;
            this.urlBuilder = urlBuilder;
            ButterKnife.bind(this, v);
        }


        @OnClick(R.id.videoDetails)
        public void onDetailsClick() {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(urlBuilder.buildYoutubeUri(videoKey));
            context.startActivity(i);
        }

        @OnClick(R.id.share_button)
        public void onShareButton() {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, videoName.getText());
            i.putExtra(Intent.EXTRA_TEXT, urlBuilder.buildYoutubeUri(videoKey).toString());
            context.startActivity(Intent.createChooser(i, context.getString(R.string.share_chooser_title)));
        }


    }
}

