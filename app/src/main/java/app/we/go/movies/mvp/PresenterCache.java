package app.we.go.movies.mvp;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class PresenterCache {


    private SimpleArrayMap<String, BasePresenter> presenters;

    public PresenterCache() {
        presenters = new SimpleArrayMap<>();
    }

    /**
     * Remove the presenter associated with the given tag
     *
     * @param key A unique tag to identify the presenter
     */
    public final void removePresenter(String key) {
        if (presenters != null) {
            presenters.remove(key);
        }
    }


    /**
     * Returns a presenter instance that will be stored and
     * survive configuration changes
     *
     * @param key A unique tag to identify the presenter

     * @param <T> The presenter type
     * @return The presenter
     */
    @Nullable
    public final <T extends BasePresenter> T getPresenter(
            String key) {

        T p = null;
        try {
            p = (T) presenters.get(key);
        } catch (ClassCastException e) {
            Log.w("PresenterActivity", "Duplicate Presenter " +
                    "tag identified: " + key + ". This could " +
                    "cause issues with state.");
        }

        return p;
    }


    public final <T extends BasePresenter> void putPresenter(String key, T presenter) {
        presenters.put(key, presenter);
    }


}
