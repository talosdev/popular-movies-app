package talosdev.movies;

//TODO in order to use normal fragment class, need to raise minSupportedSDK to 13?

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * Created by apapad on 19/11/15.
 */
public class GridFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // No need to call super because Fragment.onCreateView() return null

     //   GridView gridView= (GridView) inflater.inflate(R.layout.grid_fragment, container, false);
        View view= inflater.inflate(R.layout.grid_fragment, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.moviesGrid);
        List<String> list = ((MainActivity) getActivity()).getPosterURLs();

        gridView.setAdapter(new GridViewArrayAdapter(getActivity(), R.layout.grid_item, list));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
