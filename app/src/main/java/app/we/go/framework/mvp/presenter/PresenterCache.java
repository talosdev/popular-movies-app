package app.we.go.framework.mvp.presenter;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

import app.we.go.framework.mvp.MVP;
import app.we.go.framework.log.LOG;

/**
 * Cache for {@link Presenter}s.
 * <p/>
 * <p/>
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class PresenterCache {


    private SimpleArrayMap<String, CacheablePresenter> presenters;

    public PresenterCache() {
        presenters = new SimpleArrayMap<>();
    }


    /**
     * Stores a presenter to the cache.
     *
     * @param tag       A unique tag to identify the presenter
     * @param presenter The presenter
     * @param <T>       The type of the presenter
     */
    private final <T extends CacheablePresenter> void putPresenter(String tag, T presenter) {
        presenters.put(tag, presenter);
    }


    /**
     * Returns a presenter instance that will be stored and
     * survive configuration changes.
     * If the presenter is present in the cache, the method will just return it,
     * otherwise it will create a new instance, store it in the cache, and then
     * return it.
     *
     * @param <T> The presenter type
     * @param tag A unique tag to identify the presenter
     * @param factory
     * @return The presenter
     */
    @Nullable
    public final <T extends CacheablePresenter> T getPresenter(
            String tag, PresenterFactory factory) {

        LOG.d(MVP.TAG, "Requesting presenter with tag %s", tag);

        T p = (T) presenters.get(tag);
        if (p == null) {
            LOG.d(MVP.TAG, "Presenter for tag %s not found in cache, creating it...", tag);
            p = (T) factory.createPresenter(tag);
            putPresenter(tag, p);
        } else {
            LOG.d(MVP.TAG, "Presenter for tag %s found in cache", tag);
        }
        return p;
    }


    /**
     * Remove the presenter associated with the given tag
     *
     * @param tag A unique tag to identify the presenter
     */
    public final void removePresenter(String tag) {
        if (presenters != null) {
            presenters.remove(tag);
        }
    }


}
