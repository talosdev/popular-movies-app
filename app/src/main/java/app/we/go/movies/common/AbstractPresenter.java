package app.we.go.movies.common;

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

}
