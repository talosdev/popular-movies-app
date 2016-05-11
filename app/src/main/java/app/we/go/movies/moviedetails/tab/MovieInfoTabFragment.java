package app.we.go.movies.moviedetails.tab;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.moviedetails.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import app.we.go.movies.remote.json.Movie;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieInfoTabFragment extends Fragment implements MovieDetailsContract.InfoView {

    @Bind(R.id.releaseDate)
    TextView releaseDateView;

    @Bind(R.id.vote_average)
    TextView voteAverageView;

    @Bind(R.id.vote_count)
    TextView voteCountView;

    @Bind(R.id.movieDescription)
    TextView descriptionView;

    @Inject
    MovieDetailsContract.Presenter presenter;



    public static MovieInfoTabFragment newInstance() {
        MovieInfoTabFragment f = new MovieInfoTabFragment();

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindInfoView(this);

    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // container might be null when there is a configuration change from
        // two-pane to one-pane, and this fragment is reloaded, but without container
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.movie_details_info_tab, container, false);

        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void displayInfo(Movie movie) {
        if (getView() == null) {
            return;
        }

        descriptionView.setText(movie.overview);

        // TODO move this to presenter
        if (movie.releaseDate != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String dateFormat = sharedPreferences.getString(
                    getResources().getString(R.string.pref_dateFormat_key),
                    getResources().getString(R.string.pref_dateFormat_value_a));
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

            releaseDateView.setText(sdf.format(movie.releaseDate));
        } else {
            releaseDateView.setText(getResources().getString(R.string.unavailable));
            releaseDateView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }

        voteAverageView.setText(movie.voteAverage + "");
        voteCountView.setText("(" + movie.voteCount + " votes)");
    }
}