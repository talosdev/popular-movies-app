package app.we.go.movies.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import app.we.go.movies.R;
import app.we.go.movies.SortByChangedCallback;
import app.we.go.movies.common.BaseActivity;
import app.we.go.movies.constants.Fragments;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.listener.MovieSelectedCallback;
import app.we.go.movies.moviedetails.MovieDetailsFragment;
import app.we.go.movies.moviedetails.MovieDetailActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements MovieSelectedCallback, SortByChangedCallback {

    private boolean twoPane;


    @Bind(R.id.sort_by_spinner)
    Spinner spinner;


    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sortOptions,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(Tags.NAVBAR, String.format("Navigation item selected: itemPosition=%d, itemId=%d", position, id));

                Log.d(Tags.LIFECYCLE, String.format("Adding fragment %d", position));

                MovieListFragment f = (MovieListFragment) getSupportFragmentManager().findFragmentByTag("TAG_SORT_BY_" + id);
                if (f == null) {
                    f = MovieListFragment.newInstance(SortByCriterion.byIndex((int) id));
                    getSupportFragmentManager().
                            beginTransaction().
                            add(R.id.movie_list_frame, f, "TAG_SORT_BY_" + id).
                            commit();
                } else {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.movie_list_frame, f, "TAG_SORT_BY_" + id).
                            commit();
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        if (savedInstanceState == null) {
//            Log.d(Tags.LIFECYCLE, String.format("Adding fragment %d", -1));
//            getSupportFragmentManager().
//                    beginTransaction().
//                    replace(R.id.movie_list_frame, MovieListFragment.newInstance(SortByCriterion.POPULARITY), Fragments.MOVIE_LIST_FRAGMENT).
//                    commit();
//        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // The existence of the detail frame in the activity will tell us if we are on
        // mobile or on tablet
        if (findViewById(R.id.detail_frame) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.detail_frame, new MovieDetailsFragment(), Fragments.MOVIE_DETAILS_FRAGMENT_TAG).
                        commit();
            }

        } else {
            twoPane = false;
        }
    }

    @DebugLog
    @Override
    protected void onResume() {
        super.onResume();
    }

    @DebugLog
    @Override
    protected void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @DebugLog
    @Override
    protected void onStop() {
        super.onStop();
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMovieSelected(long movieId, String posterPath) {

        if (twoPane) {
            // TABLET
            MovieDetailsFragment details = MovieDetailsFragment.newInstance(movieId, posterPath);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame, details).addToBackStack(null)
                    //  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            // MOBILE
            Intent intent = MovieDetailActivity.newIntent(this, movieId, posterPath);
            startActivity(intent);
        }
    }


    @Override
    public void sortByChanged(SortByCriterion sortBy) {
        int index = sortBy.getIndex();
        spinner.setSelection(index);
    }


}
