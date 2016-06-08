package app.we.go.movies.features.moviedetails.dependency;

import app.we.go.movies.dependency.FragmentScope;
import app.we.go.movies.features.moviedetails.tab.VideosTabFragment;
import dagger.Subcomponent;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@FragmentScope
@Subcomponent(modules = {MovieVideosModule.class})
public interface MovieVideosComponent {
    void inject(VideosTabFragment videosTabFragment);
}