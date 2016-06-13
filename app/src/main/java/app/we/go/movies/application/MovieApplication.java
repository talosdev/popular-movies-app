package app.we.go.movies.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.facebook.stetho.Stetho;

import app.we.go.movies.dependency.ApplicationAndroidModule;
import app.we.go.movies.dependency.ApplicationComponent;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.DaggerApplicationComponent;
import app.we.go.movies.dependency.DatabaseModule;
import app.we.go.movies.dependency.ServiceModule;

/**
 * Created by apapad on 9/03/16.
 */
public class MovieApplication extends Application {

    @VisibleForTesting
    protected ApplicationComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        initStetho();

    }

    private void initStetho() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    @VisibleForTesting
    protected void createApplicationComponent() {
        component = DaggerApplicationComponent.
                builder().
                applicationModule(new ApplicationModule()).
                serviceModule(new ServiceModule()).
                applicationAndroidModule(new ApplicationAndroidModule(this)).
                databaseModule(new DatabaseModule(this)).

                build();

    }

    public ApplicationComponent getComponent() {
        if (component == null) {
            createApplicationComponent();
        }
        return component;
    }


    /**
     * Static method that can be used by any class that isn't a Context, but
     * does have access to Context object, in order to check the Application object.
     *
     * @param context
     * @return
     */
    public static MovieApplication get(Context context) {
        return (MovieApplication) context.getApplicationContext();
    }


}
