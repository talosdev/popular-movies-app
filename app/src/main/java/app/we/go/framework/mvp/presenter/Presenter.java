package app.we.go.framework.mvp.presenter;

import android.support.annotation.Nullable;

import app.we.go.framework.mvp.view.ViewMVP;

/**
 * MVP presenter interface.
 * Defines main lifecycle/support methods.
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface Presenter<V extends ViewMVP> {

    /**
     * Binds a view to the presenter. Should be called in the appropriate lifecycle method
     * of the class that implements the View.
     * @param view
     */
    void bindView(V view);

    /**
     * Unbinds the view. Should be called in the appropriate lifecycle method
     * of the class that implements the View.
     * @param view
     */
    void unbindView();

    /**
     * Returns the bound view. Since it is {@link Nullable}, calls to this method should
     * be guarded by a call to the utility method {@link Presenter#isViewBound()}.
     * @return
     */
    @Nullable
    V getBoundView();


    /**
     * Utility method that checks whether there is a view bound to the presenter.
     * @return
     */
    boolean isViewBound();

    /**
     * Cleanup method that should be called in the appropriate  circumstances of the view's
     * lifecycle that indicate that the view is about to die forever, so this particular instance
     * of the presenter won't be needed anymore.
     */
    void clear();
}
