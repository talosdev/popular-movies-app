package app.we.go.framework.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import app.we.go.framework.mvp.presenter.Presenter;
import app.we.go.framework.log.Tags;
import app.we.go.framework.log.LOG;
import butterknife.ButterKnife;

/**
 * Base class for a {@link ViewMVP} that is implemented by a {@link Fragment}.
 * The {@link Presenter} for this view is annotated with {@link Inject} and
 * is expected to be injected by a DI framework.
 * Butterknife dependency is assumed.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class MVPFragment<P extends Presenter> extends Fragment
        implements ViewMVP {

    @Inject
    protected P presenter;

    @Inject
    protected Context context;

    /**
     * In this methods, subclasses should handle all the DI work, primarily taking care of
     * setting the {#presenter}.
     * Typically the Dagger2 component should be created/accessed and its inject method should
     * be called.
     */
    protected abstract void injectDependencies();

    /**
     * Binds butterknife componencts, calls {#injectDependencies} to take care of the instance's
     * dependencies and bind this view to the (injected) presenter.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        injectDependencies();
        presenter.bindView(this);
    }

    /**
     * Unbinds this view from the presenter.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }


    /**
     * If there is an exception, logs it.
     * Shows a Toast.
     * @param logMessage
     * @param resourceId
     * @param t
     */
    @Override
    public void showError(String logMessage, int resourceId, @Nullable Throwable t) {
        if (t == null) {
            LOG.e(Tags.GEN, logMessage);
        } else {
            LOG.e(Tags.GEN, t, logMessage);
        }

        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
    }
}
