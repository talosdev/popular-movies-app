package app.we.go.movies.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.listener.MovieInfoListener;
import app.we.go.movies.listener.MovieReviewsListener;
import app.we.go.movies.listener.MovieTrailerListener;
import app.we.go.movies.remote.FetchMovieDetailsTask;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.ui.tab.MovieInfoTabFragment;
import app.we.go.movies.ui.tab.MovieReviewsTabFragment;
import app.we.go.movies.ui.tab.MovieTrailerTabFragment;
import app.we.go.movies.ui.tab.MovieDetailsPagerAdapter;
import hugo.weaving.DebugLog;

import static app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieInfoListener {


    private static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    public static final int ICON_FAV_SEL = R.drawable.ic_favorite_blue_24dp;
    public static final int ICON_FAV_UNSEL = R.drawable.ic_favorite_border_blue_24dp;


    private URLBuilder urlBuilder;

    // holds the current movie being displayed
    private Movie currentMovie;
    // we need to store separately the id of the movie and the actual movie object
    // because the id comes is known on creation of the fragment, but the full details object
    // is loaded asynchronously
    private long currentMovieId;


    private boolean currentMovieIsFavorite;
    private ImageView imageView;
    private TextView titleView;

    private MenuItem favItem;
    private MovieDetailsPagerAdapter pagerAdapter;
    private ViewPager pager;
    private MovieInfoListener movieInfoListener;
    private MovieReviewsListener movieReviewsListener;
    private MovieTrailerListener movieTrailerListener;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    public static MovieDetailsFragment newInstance(long movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // persist the current movie
        super.onSaveInstanceState(outState);
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

        setHasOptionsMenu(true);

        // Do not inflate the layout, if there are no arguments, for example
        // when the application is opened in two-pane mode, and so there is no
        // current movie selected to show in the details pane.
        if (getArguments() != null) {
            View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);


            List<Fragment> tabFragments = buildTabFragments();

            pagerAdapter = new MovieDetailsPagerAdapter(getChildFragmentManager(), tabFragments);

            pager = (ViewPager)rootView.findViewById(R.id.details_pager);
            pager.setAdapter(pagerAdapter);

            imageView = (ImageView) rootView.findViewById(R.id.imageView);
            imageView.forceLayout();
            titleView = (TextView) rootView.findViewById(R.id.movieTitle);


            return rootView;
        } else {
            return null;
        }

    }

    private List<Fragment> buildTabFragments() {
        long movieId = getArguments().getLong(Args.ARG_MOVIE_ID);

        List<Fragment> tabFragments = new ArrayList<>();
        MovieInfoTabFragment movieInfoTabFragment = MovieInfoTabFragment.newInstance();
        tabFragments.add(movieInfoTabFragment);
        movieInfoListener = movieInfoTabFragment;

        MovieReviewsTabFragment movieReviewsTabFragment = MovieReviewsTabFragment.newInstance(movieId);
        tabFragments.add(movieReviewsTabFragment);
        movieReviewsListener = movieReviewsTabFragment;

        MovieTrailerTabFragment movieTrailerTabFragment = MovieTrailerTabFragment.newInstance();
        tabFragments.add(movieTrailerTabFragment);
        movieTrailerListener = movieTrailerTabFragment;

        return tabFragments;
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        // TODO
        // This is a hack for the problem I was facing with configuration changes from
        // two-pane landscape to one-pane portrait. If getView is null, we skip all
        // the logic of the fragment. For this to work correctly, the onCreateView method
        // must return a null view in the cases that this is happening.
        if (getView() != null) {
            if (getArguments() != null) {
                long argMovieId = getArguments().getLong(Args.ARG_MOVIE_ID);
                if (currentMovie == null || argMovieId != currentMovie.id) {
                    currentMovieId = argMovieId;
                    FetchMovieDetailsTask fetcher = new FetchMovieDetailsTask(this);
                    fetcher.execute(argMovieId);
                } else {
                    onMovieInfoReceived(currentMovie);
                }
            }
        }
    }


    @Override
    public void onMovieInfoReceived(Movie movie) {
        currentMovie = movie;

        movieInfoListener.onMovieInfoReceived(movie);
        updateUI(movie);

    }

    private void updateUI(Movie movie) {
        titleView.setText(movie.title);

        final Movie m = movie;

        // Hack to make sure that the imageView will have finished being laied out
        // when we try to load the image into it, otherwise the width and height are 0
        // especially when reloading from the bundle (after a configuration change, or back button)
        imageView.post(new Runnable() {
            @Override
            public void run() {
                int width = imageView.getWidth(); // in pixels

                String backdropURL = URLBuilder.buildBackdropPath(m.backdropPath, width);

                Log.d(Tags.REMOTE, String.format("Requesting backdrop image: %s", backdropURL));

                if (m.backdropPath != null) {
                    Picasso.
                            with(getActivity()).
                            load(backdropURL).
                            resize(imageView.getWidth(), imageView.getHeight()).
                            centerCrop().
                            into(imageView);
                } else {
                    Picasso.
                            with(getActivity()).
                            //TODO
                                    load(R.drawable.movie512).
                            into(imageView);
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_actions, menu);

        favItem = menu.findItem(R.id.menu_favorite);

        Cursor c1 = getActivity().getContentResolver().query(
                FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                null,
                null,
                null,
                null);
        setFavoriteActive(c1.moveToFirst());
    }

    /**
     * Depending on the boolean value marks/unmarks the current movie as favorite and
     * sets the correct icon.
     * @param active
     */
    private void setFavoriteActive(boolean active) {
        if (active) {
            favItem.setIcon(ICON_FAV_SEL);
            currentMovieIsFavorite = true;
        } else {
            favItem.setIcon(ICON_FAV_UNSEL);
            currentMovieIsFavorite = false;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                // currentMovie hasn't been loaded yet
                if (currentMovie == null) {
                    return false;
                }
                if (currentMovieIsFavorite) {
                    getActivity().getContentResolver().delete(
                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                            null,
                            null);
                    setFavoriteActive(false);
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(FavoriteMovieEntry.COLUMN_POSTER_PATH, currentMovie.posterPath);
                    getActivity().getContentResolver().insert(
                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
                            cv);
                    setFavoriteActive(true);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
                return super.onOptionsItemSelected(item);
    }

    @DebugLog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    public void onStop() {
        super.onStop();
    }
}
