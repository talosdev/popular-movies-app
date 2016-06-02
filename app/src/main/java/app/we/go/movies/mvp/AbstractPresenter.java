package app.we.go.movies.mvp;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.TMDBError;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class AbstractPresenter<V extends BaseView> implements  BasePresenter<V> {

    private final String tag;
    private final PresenterCache cache;

    protected V boundView;

    public AbstractPresenter(PresenterCache cache, String tag) {
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

    protected void onCallError(String logMessage, int resourceId, TMDBError error) {
        if (getBoundView() != null) {
            getBoundView().showError(logMessage +
                    ": " + error.getStatusCode() + " - " + error.getStatusMessage() , resourceId, null);
        }
    }



    protected void onCallFail(String logMessage, int resourceId, Throwable t) {
        if (getBoundView() != null) {
            getBoundView().showError(logMessage, R.string.error_network, t);
        }
    }

    @Override
    public boolean isViewBound() {
        return getBoundView() != null;
    }

    @Override
    public void clear() {
        cache.removePresenter(tag);
    }
}
