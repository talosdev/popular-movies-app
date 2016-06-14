package app.we.go.movies.remote;

import android.support.annotation.Nullable;

/**
 * Fake {@link URLBuilder} subclass that can be used for hermetic tests.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class FakeURLBuilder extends URLBuilder {

    public FakeURLBuilder() {
        this.getClass().getClassLoader().getResource("movie-list.json");
    }

    /**
     * Instead of returning a remote URL, we return a local file URL.
     * @param poster
     *  Ignored by this implementation
     * @param posterWidth
     *  Ignored by this implementation
     * @return
     */
    @Nullable
    @Override
    public String buildPosterUrl(String poster, int posterWidth) {
        return "file:///android_asset/poster.jpg";
    }

}
