package app.we.go.movies.application;

import app.we.go.movies.dependency.AndroidTestSchedulersModule;
import app.we.go.movies.dependency.ApplicationAndroidModule;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.DaggerAndroidTestApplicationComponent;
import app.we.go.movies.dependency.DatabaseModule;
import app.we.go.movies.dependency.MockServiceModule;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TestMoviesApplication extends MovieApplication {

    @Override
    protected void createApplicationComponent() {
        component = DaggerAndroidTestApplicationComponent.
                builder().
                applicationModule(new ApplicationModule()).
                applicationAndroidModule(new ApplicationAndroidModule(this)).
                schedulersModule(new AndroidTestSchedulersModule()).
                serviceModule(new MockServiceModule()).
                databaseModule(new DatabaseModule(this)).
                build();
    }



}
