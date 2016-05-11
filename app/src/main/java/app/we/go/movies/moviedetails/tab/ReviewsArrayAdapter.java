package app.we.go.movies.moviedetails.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.remote.json.Review;

/**
 * Created by apapad on 1/03/16.
 */
public class ReviewsArrayAdapter extends ArrayAdapter<Review> {


    private final LayoutInflater inflater;
    private final int resource;
    private final List<Review> reviews;

    public ReviewsArrayAdapter(Context context, int resource, List<Review> reviews, LayoutInflater layoutInflater){
        super(context, resource, reviews);
        this.inflater = layoutInflater;
        this.resource = resource;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if(rowView == null){
                rowView = inflater.inflate(resource, parent, false);
            view = new ViewHolder();
            view.reviewAuthor = (TextView) rowView.findViewById(R.id.reviewAuthor);
            view.reviewContent = (TextView) rowView.findViewById(R.id.reviewContent);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        Review r = reviews.get(position);
        view.reviewAuthor.setText(r.author);
        view.reviewContent.setText(r.content);

        return rowView;

    }

    protected static class ViewHolder{
        protected TextView reviewAuthor;
        protected TextView reviewContent;
    }
}
