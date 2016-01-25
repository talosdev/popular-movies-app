package com.talosdev.movies.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talosdev.movies.constants.Tags;

/**
 * Abstract based class for Fragments, that logs calls to lifecycle methods.
 * TODO move to personal library
 * Created by apapad on 25/01/16.
 */
public abstract class LoggedFragment extends Fragment {

    private static final String TAG = Tags.LIFECYCLE;


    @Override
    public void onStart() {
        log("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }


    @Override
    public void onAttach(Context context) {
        log("onAttach");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        log("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        log("onDestroyView");
        super.onDestroyView();
    }

    private void log(String methodName) {
        Log.d(TAG,
                String.format(
                        "Called method %s of fragment %s",
                        methodName,
                        this.getClass().getSimpleName())
        );

    }


}
