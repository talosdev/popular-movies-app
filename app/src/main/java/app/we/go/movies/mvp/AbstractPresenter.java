package app.we.go.movies.mvp;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.TMDBError;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class AbstractPresenter<V extends BaseView> implements  BasePresenter<V> {

    protected V boundView;


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
}
