package app.we.go.movies.ui.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.callbacks.MovieDetailsCallback;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.ui.fragments.MovieInfoTabFragment;
import app.we.go.movies.ui.fragments.MovieReviewsTabFragment;
import app.we.go.movies.ui.fragments.MovieTrailerTabFragment;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieDetailsPagerAdapter extends FragmentPagerAdapter implements MovieDetailsCallback {

    private static final int NUM_TABS = 3;
    private final List<Fragment> tabFragments = new ArrayList<>(NUM_TABS);
    // TODO get from resources
    public static final String[] TAB_TITLES = new String[]{"INFO", "REVIEWS", "TRAILER"};
    private Movie currentMovie;


    public MovieDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
        tabFragments.add(MovieInfoTabFragment.newInstance(this));
        tabFragments.add(MovieReviewsTabFragment.newInstance(this));
        tabFragments.add(MovieTrailerTabFragment.newInstance(this));


    }


    // Hack to make sure that on orientation changes, the existing fragment is
    // reused
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        tabFragments.set(position, fragment);
        return fragment;
 }


    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public Fragment getItem(int position) {

        return tabFragments.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }



    @Override
    public void onMovieDetailsReceived(Movie movie) {
        currentMovie = movie;
        for (Fragment f: tabFragments) {
            ((MovieDetailsCallback) f).onMovieDetailsReceived(movie);
        }
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }
}



