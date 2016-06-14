package app.we.go.movies.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.we.go.movies.R;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;
    private final Context context;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
        /** TODO
         * Is this the best way to check SharedPrefs?
         */
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String formatDate(@Nullable Date date) {

        if (date != null) {
            String dateFormat = sharedPreferences.getString(
                    context.getResources().getString(R.string.pref_dateFormat_key),
                    context.getResources().getString(R.string.pref_dateFormat_value_a));
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.format(date);

        } else {
            return context.getResources().getString(R.string.unavailable);
        }
    }

}
