package app.we.go.movies.dependency;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.db.CupboardFavoriteMovieDAO;
import app.we.go.movies.db.CupboardSQLiteOpenHelper;
import app.we.go.movies.db.FavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Application-scoped module with android dependencies.
 *
 * Created by apapad on 9/03/16.
 */
@Module
public class ApplicationAndroidModule {

    Context context;

    public ApplicationAndroidModule(Context context) {
        this.context = context;
    }



    @Provides
    @Singleton
    public SharedPreferencesHelper provideSharedPreferencesHelper() {
        return new SharedPreferencesHelper(context);
    }

    @Provides
    @Singleton
    public SQLiteOpenHelper provideSqLiteOpenHelper() {
        return new CupboardSQLiteOpenHelper(context, CupboardSQLiteOpenHelper.DATABASE_NAME);
    }

    // NOT A SINGLETON, in order to not maintain a connection to the database always
    @Provides
    public FavoriteMovieDAO proFavoriteMovieDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        return new CupboardFavoriteMovieDAO(sqLiteOpenHelper.getWritableDatabase());
    }



}
