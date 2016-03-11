package app.we.go.movies.dependency;

/**
 * Created by apapad on 9/03/16.
 */

import javax.inject.Singleton;

import app.we.go.movies.ui.tab.MovieInfoTabFragment;
import app.we.go.movies.ui.tab.MovieReviewsTabFragment;
import app.we.go.movies.ui.tab.VideosTabFragment;
import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MovieInfoTabFragment f);
    void inject(VideosTabFragment f);
    void inject(MovieReviewsTabFragment f);

}
