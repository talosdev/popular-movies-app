package app.we.go.movies.moviedetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.common.BaseView;
import app.we.go.movies.constants.Args;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.moviedetails.dependency.HasMovieDetailsComponent;
import app.we.go.movies.moviedetails.tab.MovieDetailsPagerAdapter;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.util.LOG;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {


    private long currentMovieId;
    private String currentMoviePosterPath;

    @BindDrawable(R.drawable.ic_favorite_blue_24dp)
    Drawable icon_favorite_selected;

    @BindDrawable(R.drawable.ic_favorite_border_blue_24dp)
    Drawable icon_favorite_unselected;


    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.movieTitle)
    TextView titleView;

    @Bind(R.id.details_pager)
    ViewPager pager;

    @Inject
    MovieDetailsContract.Presenter presenter;

    @Inject
    Context context;

    private MenuItem favItem;
    private MovieDetailsPagerAdapter pagerAdapter;


    // Variable that is set to true when this fragment is headless (ie container==null)
    // This might happen when changing from dual-pane to single-pane
    private boolean headless;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    public static MovieDetailsFragment newInstance(long movieId, String posterPath) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle b = new Bundle();
        b.putLong(Args.ARG_MOVIE_ID, movieId);
        b.putString(Args.ARG_POSTER_PATH, posterPath);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentMovieId = getArguments().getLong(Args.ARG_MOVIE_ID);
            currentMoviePosterPath = getArguments().getString(Args.ARG_POSTER_PATH);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // container might be null when there is a configuration change from
        // two-pane to one-pane, and this fragment is reloaded, but without container
        if (container == null) {
            headless = true;
        }

        // Do not inflate the layout, if there are no arguments, for example
        // when the application is opened in two-pane mode, and so there is no
        // current movie selected to show in the details pane.
        if (getArguments() != null) {
            return inflater.inflate(R.layout.movie_details_fragment, container, false);

        } else {
            return null;
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);
        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindView(this);

        pagerAdapter = new MovieDetailsPagerAdapter(getChildFragmentManager(), currentMovieId);

        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);


        imageView.forceLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_actions, menu);

        favItem = menu.findItem(R.id.menu_favorite);
        presenter.checkFavorite(currentMovieId);

    }


    @Override
    public void toggleFavorite(boolean isFavorite) {
        favItem.setIcon(isFavorite ? icon_favorite_selected : icon_favorite_unselected);
    }

    @Override
    public void displayTitle(String title) {
        LOG.d(Tags.REMOTE, "Got movie title \'%s\' and will display it", title);

        titleView.setText(title);
    }

    @Override
    public void displayImage(final String imagePath) {
        // Hack to make sure that the imageView will have finished being laied out
        // when we try to load the image into it, otherwise the width and height are 0
        // especially when reloading from the bundle (after a configuration change, or back button)
        imageView.post(new Runnable() {
            @Override
            public void run() {
                int width = imageView.getWidth(); // in pixels

                String backdropURL = URLBuilder.buildBackdropPath(imagePath, width);

                Log.d(Tags.REMOTE, String.format("Requesting backdrop image: %s", backdropURL));

                if (imagePath != null) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        LOG.v(Tags.UI, "ITEM: " + item.getItemId());

        switch (item.getItemId()) {
            case R.id.menu_favorite:
                // currentMovie hasn't been loaded yet
                if (currentMovieId == 0) {
                    return false;
                }
                presenter.onFavoriteClick(currentMovieId, currentMoviePosterPath);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
        BaseView.Helper.showError(context, logMessage, resourceId, t);
    }

}
