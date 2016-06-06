package app.we.go.framework.mvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.UUID;

import app.we.go.framework.mvp.presenter.CacheablePresenter;
import app.we.go.movies.constants.Tags;
import hugo.weaving.DebugLog;

/**
 * Base class for a {@link ViewMVP} that is implemented by a {@link Fragment} and uses a
 * {@link CacheablePresenter}.
 * It uses the logic from
 * http://blog.bradcampbell.nz/mvp-presenters-that-survive-configuration-changes-part-1/
 * to decide when it has to remove the presenter from the cache.
 *
 * The main idea is that the class holds the actual's presenter's tag as instance state and
 * tries to get it from cache and reconnect to it, if it is available.
 *
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public abstract class CacheablePresenterBasedFragment<P extends CacheablePresenter>
        extends MVPFragment<P> {
    private String presenterTag;
    private boolean isDestroyedBySystem;
    private boolean fromCache;


    /**
     * Tries to resolve the {@link CacheablePresenterBasedFragment#presenterTag} before calling
     * <code>super</code>.
     * First the method looks in the <code>savedInstanceState</code> and if there is no tag
     * stored there, it creates a new unique tag. The call to
     * {@link super#onViewCreated(View, Bundle)}
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenterTag = null;

        if (savedInstanceState != null) {
            presenterTag = savedInstanceState.getString(Tags.PRESENTER_TAG);
        }
        if (presenterTag == null) {
            presenterTag = UUID.randomUUID().toString();
        } else {
            fromCache = true;
        }

        // super assumes that presenterTag is set, so this needs to be at the end of the method
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Sets the {@link #isDestroyedBySystem} flag to true and stores the {@link #presenterTag}
     * to the instance bundle.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
        outState.putString(Tags.PRESENTER_TAG, presenterTag);
    }

    /**
     * Takes care of the initialization logic for the view, depending on the status of the presenter.
     * If the presenter was obtained from the cache, the method {@link CacheablePresenter#onRestoreFromCache()}
     * is called, otherwise the view's {@link #initViewNoCache()} is called.
     *
     * @param savedInstanceState
     */
    @DebugLog
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (fromCache) {
            presenter.onRestoreFromCache();
        } else {
            initViewNoCache();
        }
    }

    /**
     * Sets the {@link #isDestroyedBySystem} flag to false.
     */
    @Override
    public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;
    }

    /**
     * If we are certain that this fragment won't be revived later, we clear the presenter.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem) {
            presenter.clear();
        }
    }


    /**
     * For injecting a {@link CacheablePresenter}, the subclasses will need the {@link #presenterTag},
     * so we make this method final, and we call the abstract overloaded method
     * {@link #injectDependencies(String presenterTag}.
     */
    @Override
    final protected void injectDependencies() {
        injectDependencies(presenterTag);
    }


    /**
     * In this methods, subclasses should handle all the DI work, primarily taking care of
     * setting the {#presenter}.
     * Typically the Dagger2 component should be created/accessed and its inject method should
     * be called.
     */
    protected abstract void injectDependencies(String presenterTag);

    /**
     * Subclasses should include in this method the logic that the view should execute
     * when being initialized with a newly-created presenter. This logic will be skipped
     * if the view is initialized with a presenter obtained from the cache.
     * For example. a typical implementation of this method could request the presenter to fetch
     * remote data. When the presenter is obtained from the cache, it should already have
     * this data, so this call would be unnecessary.
     */
    protected abstract void initViewNoCache();

}
