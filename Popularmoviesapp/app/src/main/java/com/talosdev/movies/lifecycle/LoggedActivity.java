package com.talosdev.movies.lifecycle;

import android.app.Activity;
import android.util.Log;

import com.talosdev.movies.constants.Tags;

/**
 * Abstract based class for Activities, that logs calls to lifecycle methods.
 * TODO move to personal library
 * Created by apapad on 25/01/16.
 */
public abstract class LoggedActivity extends Activity {

    private static final String TAG = Tags.LIFECYCLE;


    @Override
    protected void onStart() {
        log("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    private void log(String methodName) {
        Log.d(TAG,
                String.format(
                        "Called method %s of activity %s",
                        methodName,
                        this.getClass().getSimpleName())
        );

    }


}
