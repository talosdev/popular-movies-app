package app.we.go.movies;

import app.we.go.movies.application.MovieApplication;
import app.we.go.movies.dependency.ApplicationAndroidModule;
import app.we.go.movies.dependency.ApplicationModule;
import application.DaggerMockApplicationComponent;
import application.MockServiceModule;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TestMoviesApplication extends MovieApplication {

    @Override
    protected void createApplicationComponent() {
        component = DaggerMockApplicationComponent.
                builder().
                applicationModule(new ApplicationModule()).
                mockServiceModule(new MockServiceModule()).
                applicationAndroidModule(new ApplicationAndroidModule(this)).                build();


    }




}
