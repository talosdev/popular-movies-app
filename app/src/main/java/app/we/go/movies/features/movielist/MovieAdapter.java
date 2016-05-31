package app.we.go.movies.features.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.model.remote.Movie;
import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by apapad on 19/03/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {


    private final Context context;
    private final MovieClickListener eventClickListener;

    private final List<Movie> movies;
    private final URLBuilder urlBuilder;

    public MovieAdapter(Context context, MovieClickListener listener, URLBuilder urlBuilder) {
        movies = new ArrayList<>();

        this.context = context;
        this.eventClickListener = listener;
        this.urlBuilder = urlBuilder;
    }


    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        if (movies != null) {
            this.movies.addAll(movies);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_item, parent, false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        final Movie m = movies.get(position);

        holder.image.setContentDescription(m.getTitle());

        String posterPath = m.getPosterPath();
        if (posterPath != null) {
            // already in pixels
            int posterWidth = context.getResources().getDimensionPixelSize(R.dimen.poster_width_grid);
            String posterUrl = urlBuilder.buildPosterUrl(posterPath, posterWidth);
            Log.v(Tags.REMOTE, String.format("Requesting poster image: %s - %d", posterUrl, position));

            Picasso.
                    with(context).
                    load(posterUrl).
                    placeholder(R.drawable.background).
                    // TODO
                            error(R.drawable.error128).
                    resizeDimen(R.dimen.poster_width_grid, R.dimen.poster_height_grid).
                    centerCrop().
                    into(holder.image);
        } else {
            Picasso.
                    with(context).
                    //TODO resolution
                            load(R.drawable.movie128).
                    into((holder.image));
        }


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickListener.onPosterClick(movies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public interface MovieClickListener {


        void onPosterClick(Movie movie);
    }



}
class MovieHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    ImageView image;




    public MovieHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

    }



}
