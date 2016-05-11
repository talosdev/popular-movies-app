package app.we.go.movies.common;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface BasePresenter<V extends BaseView> {
    void bindView(V view);

    void unbindView();

    V getBoundView();
}
