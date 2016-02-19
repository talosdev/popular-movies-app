package app.we.go.movies.ui.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import app.we.go.movies.ui.PreferencesFragment;

/**
 * Created by apapad on 25/01/16.
 */
public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

    }
}
