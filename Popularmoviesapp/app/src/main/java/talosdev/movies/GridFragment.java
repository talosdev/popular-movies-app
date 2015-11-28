package talosdev.movies;

//TODO in order to use normal fragment class, need to raise minSupportedSDK to 13?

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.List;

import talosdev.movies.constants.Intents;
import talosdev.movies.data.MoviePoster;

/**
 * Created by apapad on 19/11/15.
 */
public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null

     //   GridView gridView= (GridView) inflater.inflate(R.layout.grid_fragment, container, false);
        View view= inflater.inflate(R.layout.grid_fragment, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.moviesGrid);
        gridView.setOnItemClickListener(this);
        List<MoviePoster> list = ((MainActivity) getActivity()).getPosterURLs();
        adapter = new GridViewArrayAdapter(getActivity(), R.layout.grid_item, list);
        gridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long movieId = ((GridViewArrayAdapter) adapter).getItem(position).getMovieId();
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Intents.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }
}
