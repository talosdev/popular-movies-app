package app.we.go.movies.common;

import app.we.go.movies.R;

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

    protected void onError(String logMessage, int resourceId) {
        if (getBoundView() != null) {
            getBoundView().showError(logMessage, R.string.error_network, null);
        }
    }



    protected void onFail(String logMessage, int resourceId, Throwable t) {
        if (getBoundView() != null) {
            getBoundView().showError(logMessage, R.string.error_network, t);
        }
    }
}
