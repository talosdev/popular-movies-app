package app.we.go.movies.moviedetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import app.we.go.movies.R;
import app.we.go.movies.constants.Args;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.moviedetails.tab.MovieDetailsPagerAdapter;
import app.we.go.movies.remote.URLBuilder;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 3/01/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View{



    private long currentMovieId;

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


    // private MovieInfoListener movieInfoListener;
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
        getLoaderManager().enableDebugLogging(true);
        if (getArguments() != null) {
            currentMovieId = getArguments().getLong(Args.ARG_MOVIE_ID);


        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HasMovieDetailsComponent) getActivity()).getComponent().inject(this);

        presenter.bindView(this);

        presenter.checkFavorite(currentMovieId);
        presenter.loadMovieInfo(currentMovieId);
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

        setHasOptionsMenu(true);

        // Do not inflate the layout, if there are no arguments, for example
        // when the application is opened in two-pane mode, and so there is no
        // current movie selected to show in the details pane.
        if (getArguments() != null) {
            View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);

            ButterKnife.bind(this, rootView);

            pagerAdapter = new MovieDetailsPagerAdapter(getChildFragmentManager(), currentMovieId, presenter);

            pager.setOffscreenPageLimit(2);
            pager.setAdapter(pagerAdapter);

            imageView.forceLayout();


            return rootView;
        } else {
            return null;
        }

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_actions, menu);

        favItem = menu.findItem(R.id.menu_favorite);

    }




    @Override
    public void toggleFavorite(boolean isFavorite) {
        favItem.setIcon(isFavorite ? icon_favorite_selected : icon_favorite_unselected);
    }

    @Override
    public void displayError(@StringRes int errorResource) {
        Toast.makeText(context, errorResource, Toast.LENGTH_LONG);
    }

    @Override
    public void displayTitle(String title) {
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
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                presenter.toggleFavorite(currentMovieId);

//            case R.id.menu_favorite:
//                // currentMovie hasn't been loaded yet
//                if (currentMovieId == 0) {
//                    return false;
//                }
//                if (currentMovieIsFavorite) {
//                    getActivity().getContentResolver().delete(
//                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
//                            null,
//                            null);
//                    setFavoriteActive(false);
//                } else {
//                    ContentValues cv = new ContentValues();
//                    cv.put(FavoriteMovieEntry.COLUMN_POSTER_PATH, currentMoviePosterPath);
//                    getActivity().getContentResolver().insert(
//                            FavoriteMovieEntry.buildFavoriteMovieUri(currentMovieId),
//                            cv);
//                    setFavoriteActive(true);
//                }
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
