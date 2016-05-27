package app.we.go.movies.dependency;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
 @Singleton
 @Component(modules = {ApplicationModule.class,
         MockServiceModule.class,
         ApplicationAndroidModule.class,
         FakeURLBuilder.MockDatabaseModule.class})
 public interface MockApplicationComponent extends ApplicationComponent {
 }