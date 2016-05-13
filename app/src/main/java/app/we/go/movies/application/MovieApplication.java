package app.we.go.movies.application;

import android.app.Application;
import android.content.Context;

import app.we.go.movies.dependency.ApplicationAndroidModule;
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
                applicationAndroidModule(new ApplicationAndroidModule(this)).

                build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }


    /**
     * Static method that can be used by any class that isn't a Context, but
     * does have access to Context object, in order to get the Application object.
     * @param context
     * @return
     */
    public static MovieApplication get(Context context) {
        return (MovieApplication) context.getApplicationContext();
    }
}
