package app.we.go.framework.mvp.view;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Base interface for an MVP view.
 * Only defines a simple method for showing an error message.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface ViewMVP {

    void showError(Context context, String logMessage, int resourceId, @Nullable Throwable t);

}
