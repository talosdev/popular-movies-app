package app.we.go.movies.dependency;

import android.content.Context;

import javax.inject.Singleton;

import app.we.go.movies.helpers.SharedPreferencesHelper;
import dagger.Module;
import dagger.Provides;

/**
 * Application-scoped module with android dependencies.
 *
 * Created by apapad on 9/03/16.
 */
@Module
public class ApplicationAndroidModule {

    final Context context;

    public ApplicationAndroidModule(Context context) {
        this.context = context;
    }



    @Provides
    @Singleton
    public SharedPreferencesHelper provideSharedPreferencesHelper() {
        return new SharedPreferencesHelper(context);
    }





}
