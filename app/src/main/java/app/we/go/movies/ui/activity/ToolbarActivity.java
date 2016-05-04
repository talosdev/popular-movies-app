package app.we.go.movies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.we.go.movies.R;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class ToolbarActivity extends AppCompatActivity {
    public void initToolbar(boolean homeAsUp) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
        getSupportActionBar().setDisplayShowTitleEnabled(homeAsUp);

    }
}