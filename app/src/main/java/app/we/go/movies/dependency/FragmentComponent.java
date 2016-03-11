package app.we.go.movies.dependency;

import app.we.go.movies.ui.tab.VideosTabFragment;
import dagger.Subcomponent;

/**
 * Created by apapad on 11/03/16.
 */
@Subcomponent(modules=VideoFragmentModule.class)
public interface FragmentComponent {
    void inject(VideosTabFragment f);
}

