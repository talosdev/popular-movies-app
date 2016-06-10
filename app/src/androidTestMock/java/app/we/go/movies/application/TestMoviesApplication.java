package app.we.go.movies.application;

import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.DaggerMockApplicationComponent;
import app.we.go.movies.dependency.DatabaseModule;
import app.we.go.movies.dependency.MockApplicationAndroidModule;
import app.we.go.movies.dependency.MockServiceModule;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TestMoviesApplication extends MovieApplication {

    @Override
    protected void createApplicationComponent() {
        component = DaggerMockApplicationComponent.
                builder().
                applicationModule(new ApplicationModule()).
                mockApplicationAndroidModule(new MockApplicationAndroidModule(this)).
                mockServiceModule(new MockServiceModule()).
                databaseModule(new DatabaseModule(this)).
                build();


    }




}
