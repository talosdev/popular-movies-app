package app.we.go.movies.features.moviedetails.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.Video;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoArrayAdapter extends ArrayAdapter {
    private final LayoutInflater inflater;
    private final int rowLayout;
    private final List<Video> videos;
    private final VideoClickListener videoClickListener;
    private final Context context;


    public VideoArrayAdapter(Context context, int rowLayout, List<Video> videos, LayoutInflater layoutInflater,
                             VideoClickListener videoClickListener) {
        super(context, rowLayout, videos);
        this.context = context;
        this.inflater = layoutInflater;
        this.rowLayout = rowLayout;
        this.videos = videos;

        this.videoClickListener = videoClickListener;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if(rowView == null){
            rowView = inflater.inflate(rowLayout, parent, false);
            viewHolder = new ViewHolder(rowView, videoClickListener);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Video v = videos.get(position);
        viewHolder.videoName.setText(v.getName());
        viewHolder.videoDetails.setText(
                String.format(
                        context.getResources().getString(R.string.video_desc),
                        v.getType(),
                        v.getSite()));
        viewHolder.videoKey = v.getKey();

        return rowView;

    }

    protected static class ViewHolder{
        @BindView(R.id.videoName)
        TextView videoName;

        @BindView(R.id.videoDetails)
        TextView videoDetails;

        String videoKey;

        private final VideoClickListener videoClickListener;

        public ViewHolder(View v, VideoClickListener videoClickListener) {
            ButterKnife.bind(this, v);
            this.videoClickListener = videoClickListener;
        }

        @OnClick(R.id.videoDetails)
        public void onVideoClick() {
            videoClickListener.onVideoDetailsClick(videoKey);
        }


        @OnClick(R.id.share_button)
        public void onVideoShareClick() {
            videoClickListener.onVideoShareClick(videoKey, videoName.getText().toString());
        }


    }

    /**
     * Listener interface that the container of the list should implement (or provide an implementation of)
     * so that it can be notified about actions inside the item view, and handle them appropriately.
     */
    public interface VideoClickListener {
        void onVideoDetailsClick(String videoKey);

        void onVideoShareClick(String videoKey, String videoName);
    }
}

