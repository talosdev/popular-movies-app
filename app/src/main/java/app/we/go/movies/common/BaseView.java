package app.we.go.movies.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.util.LOG;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface BaseView {

    void showError(String logMessage, int resourceId, @Nullable Throwable t);


    class Helper {
        public static void showError(Context context, String logMessage, int resourceId, @Nullable Throwable t) {
            if (t == null) {
                LOG.d(Tags.GEN, logMessage);
            } else {
                LOG.d(Tags.GEN, t, logMessage);
            }

            Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
        }
    }
}
