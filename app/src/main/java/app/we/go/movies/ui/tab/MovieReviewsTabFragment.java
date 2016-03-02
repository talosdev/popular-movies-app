package app.we.go.movies.ui.tab;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.listener.MovieInfoListener;
import app.we.go.movies.listener.MovieReviewsListener;
import app.we.go.movies.remote.FetchMovieReviewsTask;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.ui.ReviewsArrayAdapter;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieReviewsTabFragment extends ListFragment implements MovieInfoListener, MovieReviewsListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_review_tab, container, false);

        return v;
    }

    public static MovieReviewsTabFragment newInstance(long movieId) {
        MovieReviewsTabFragment f = new MovieReviewsTabFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        f.setArguments(b);
        return f;
    }

    // TODO why onActivityCreated?
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ReviewsArrayAdapter(getActivity(), R.layout.review_row, new ArrayList<Review>(), getActivity().getLayoutInflater());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            FetchMovieReviewsTask task = new FetchMovieReviewsTask((ArrayAdapter) getListAdapter());
            task.execute(getArguments().getLong(Args.ARG_MOVIE_ID));
        }
    }

    @Override
    public void onMovieInfoReceived(Movie movie) {

    }

    @Override
    public void onMovieReviewsReceived(Movie movie) {

    }
}
