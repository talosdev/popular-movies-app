package app.we.go.movies.features.moviedetails.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.Review;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apapad on 1/03/16.
 */
public class ReviewsArrayAdapter extends ArrayAdapter<Review> {


    private final LayoutInflater inflater;
    private final int rowLayout;
    private final List<Review> reviews;

    public ReviewsArrayAdapter(Context context, int rowLayout, List<Review> reviews, LayoutInflater layoutInflater){
        super(context, rowLayout, reviews);
        this.inflater = layoutInflater;
        this.rowLayout = rowLayout;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if(rowView == null){
                rowView = inflater.inflate(rowLayout, parent, false);
            viewHolder = new ViewHolder(rowView);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Review r = reviews.get(position);
        viewHolder.reviewAuthor.setText(r.getAuthor());
        viewHolder.reviewContent.setText(r.getContent());

        return rowView;

    }

    protected static class ViewHolder{
        @Bind(R.id.reviewAuthor)
        protected TextView reviewAuthor;

        @Bind(R.id.reviewContent)
        protected TextView reviewContent;

        public ViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
        }
    }

    @Override
    public void addAll(Collection<? extends Review> collection) {
        super.addAll(collection);
    }
}
