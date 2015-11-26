package talosdev.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import talosdev.movies.constants.Intents;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(getIntent().getLongExtra(Intents.EXTRA_MOVIE_ID, 0L) + "");


    }
}
