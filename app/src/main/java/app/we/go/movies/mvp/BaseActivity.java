package app.we.go.movies.mvp;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public Object getLastCustomNonConfigurationInstance() {
        return super.getLastCustomNonConfigurationInstance();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }
}