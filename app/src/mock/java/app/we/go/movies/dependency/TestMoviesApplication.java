package app.we.go.movies.dependency;

import app.we.go.movies.application.MovieApplication;

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
                applicationAndroidModule(new ApplicationAndroidModule(this)).
                mockDatabaseModule(new FakeURLBuilder.MockDatabaseModule()).
                build();


    }




}
