package app.we.go.movies.mvp;

/**
 * Created by apapad on 11/03/16.
 */
public interface MvpPresenter<V extends MvpView> {

    void bindView(V view);

    void unbindView();

}
