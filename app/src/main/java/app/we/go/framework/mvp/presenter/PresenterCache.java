package app.we.go.framework.mvp.presenter;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

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

        T p = (T) presenters.get(tag);
        if (p == null) {
            p = (T) factory.createPresenter(tag);
            putPresenter(tag, p);
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
