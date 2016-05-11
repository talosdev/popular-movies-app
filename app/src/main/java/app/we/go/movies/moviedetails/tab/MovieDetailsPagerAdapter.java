package app.we.go.movies.moviedetails.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import app.we.go.movies.listener.MovieInfoListener;
import app.we.go.movies.moviedetails.MovieDetailsContract;
import hugo.weaving.DebugLog;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieDetailsPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 3;
    private final long movieId;
    private MovieDetailsContract.Presenter presenter;
    private Fragment[] tabFragments = new Fragment[NUM_TABS];
    // TODO get from resources
    public static final String[] TAB_TITLES = new String[]{"INFO", "REVIEWS", "VIDEOS"};


    public MovieDetailsPagerAdapter(FragmentManager fm, long movieId, MovieDetailsContract.Presenter presenter) {
        super(fm);
        this.movieId = movieId;
        this.presenter = presenter;
        Log.d("ZZZ", "Constructor: " + tabFragments.toString());
       }


    // Hack to make sure that on orientation changes, the existing fragment is
    // reused
    @DebugLog
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        tabFragments[position] = fragment;
        Log.d("ZZZ", "Instantiate: " + tabFragments.toString() + " - " + tabFragments[position].toString());

        return fragment;
    }


    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = MovieInfoTabFragment.newInstance();
                break;
            case 1:
                f = MovieReviewsTabFragment.newInstance(movieId);
                break;
            case 2:
                f = VideosTabFragment.newInstance(movieId);
                break;
        }

        tabFragments[position] = f;

        Log.d("ZZZ", "getItem: " + tabFragments.toString() + " - " + tabFragments[position].toString());

        return f;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }


    public MovieInfoListener getMovieInfoListener() {
        return (MovieInfoListener) tabFragments[0];
    }

}



