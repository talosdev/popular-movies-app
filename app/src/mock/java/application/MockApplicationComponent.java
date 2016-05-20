package application;

import javax.inject.Singleton;

import app.we.go.movies.dependency.ApplicationAndroidModule;
import app.we.go.movies.dependency.ApplicationComponent;
import app.we.go.movies.dependency.ApplicationModule;
import dagger.Component;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
 @Singleton
 @Component(modules = {ApplicationModule.class, MockServiceModule.class, ApplicationAndroidModule.class})
 public interface MockApplicationComponent extends ApplicationComponent {
 }