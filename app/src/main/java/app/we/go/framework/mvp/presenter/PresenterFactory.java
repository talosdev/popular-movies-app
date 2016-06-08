package app.we.go.framework.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface PresenterFactory<T extends CacheablePresenter> {

    @NonNull
    T createPresenter(String tag);
}
