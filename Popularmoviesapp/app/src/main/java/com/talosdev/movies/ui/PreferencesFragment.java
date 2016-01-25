package com.talosdev.movies.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.talosdev.movies.R;

/**
 * Created by apapad on 25/01/16.
 */
public class PreferencesFragment  extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
