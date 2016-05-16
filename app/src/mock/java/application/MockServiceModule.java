package application;

import javax.inject.Singleton;

import app.we.go.movies.remote.MockTMDBServiceSync;
import app.we.go.movies.remote.TMDBService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockServiceModule  {

    @Provides
    @Singleton
    public TMDBService provideServiceModule() {
        return new MockTMDBServiceSync();
    }
}
