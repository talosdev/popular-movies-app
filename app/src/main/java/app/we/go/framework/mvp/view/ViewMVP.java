package app.we.go.framework.mvp.view;

import android.support.annotation.Nullable;

/**
 * Base interface for an MVP view.
 * Only defines a simple method for showing an error message.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface ViewMVP {

    void showError(String logMessage, int resourceId, @Nullable Throwable t);

}
