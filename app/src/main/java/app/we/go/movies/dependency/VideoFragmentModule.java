package app.we.go.movies.dependency;

import app.we.go.movies.mvp.VideosPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by apapad on 11/03/16.
 */
@FragmentScope
@Module
public class VideoFragmentModule {

    @Provides
    VideosPresenter provideVideoPresenter() {
        return new VideosPresenter();
    }


}
