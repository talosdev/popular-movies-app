package app.we.go.movies.mvp;

/**
 * Created by apapad on 11/03/16.
 */
public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    
    protected V view;


    @Override
    public void bindView(V view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }


    public V getBoundView() {
        return view;
    }
}
