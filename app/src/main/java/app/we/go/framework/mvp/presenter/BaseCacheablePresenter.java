package app.we.go.framework.mvp.presenter;

import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.R;
import app.we.go.movies.model.remote.TMDBError;

/**
 * Default implementation of {@link CacheablePresenter} that implements the
 * basic lifecycle methods. Also offers some utility methods for error handling.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class BaseCacheablePresenter<V extends ViewMVP> implements CacheablePresenter<V> {

    private final String tag;
    private final PresenterCache cache;

    protected V boundView;

    public BaseCacheablePresenter(PresenterCache cache, String tag) {
        this.cache = cache;
        this.tag = tag;
    }

    @Override
    public void bindView(V view) {
        this.boundView = view;
    }

    @Override
    public void unbindView() {
        boundView = null;
    }

    @Override
    public V getBoundView() {
        return boundView;
    }

    @Override
    public boolean isViewBound() {
        return getBoundView() != null;
    }

    @Override
    public void clear() {
        cache.removePresenter(tag);
    }

    protected void onCallError(String logMessage, int resourceId, TMDBError error) {
        if (getBoundView() != null) {
            getBoundView().showError(null, logMessage +
                    ": " + error.getStatusCode() + " - " + error.getStatusMessage() , resourceId, null);
        }
    }


    protected void onCallFail(String logMessage, int resourceId, Throwable t) {
        if (getBoundView() != null) {
            getBoundView().showError(null, logMessage, R.string.error_network, t);
        }
    }

}
