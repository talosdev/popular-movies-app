package app.we.go.movies.ui;

import android.app.Application;

import app.we.go.movies.dependency.ApplicationComponent;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.DaggerApplicationComponent;

/**
 * Created by apapad on 9/03/16.
 */
public class MovieApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.
                builder().
                applicationModule(new ApplicationModule()).
                build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
