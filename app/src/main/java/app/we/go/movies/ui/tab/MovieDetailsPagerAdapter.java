package app.we.go.movies.ui.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by apapad on 26/02/16.
 */
public class MovieDetailsPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 3;
    private List<Fragment> tabFragments;
    // TODO get from resources
    public static final String[] TAB_TITLES = new String[]{"INFO", "REVIEWS", "TRAILER"};


    public MovieDetailsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        tabFragments = fragments;
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


}



