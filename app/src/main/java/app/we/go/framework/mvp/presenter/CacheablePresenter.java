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
     * Defines the actions that the DetailsPresenter should take after being re-attached to a DetailsView.
     * Typically, the implementations should call the appropriate DetailsView methods in order to
     * populate the DetailsView with the data that the DetailsPresenter has already loaded (without, for example
     * making additional network calls).
     */
    void onRestoreFromCache();
}
