package app.we.go.framework.mvp.presenter;

import app.we.go.framework.mvp.view.ViewMVP;

/**
 * A {@link Presenter} that can be cached and re-attached to a view
 * (after configuration changes or activity recreation)
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface CacheablePresenter<V extends ViewMVP> extends Presenter<V> {

    /**
     * Defines the actions that the Presenter should take after being re-attached to a View.
     * Typically, the implementations should call the appropriate View methods in order to
     * populate the View with the data that the Presenter has already loaded (without, for example
     * making additional network calls).
     */
    void onRestoreFromCache();
}
