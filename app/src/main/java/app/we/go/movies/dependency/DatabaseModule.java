package app.we.go.movies.dependency;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import app.we.go.movies.db.CupboardFavoriteMovieDAO;
import app.we.go.movies.db.CupboardSQLiteOpenHelper;
import app.we.go.movies.db.FavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SQLiteOpenHelper provideSqLiteOpenHelper() {
        return new CupboardSQLiteOpenHelper(context, CupboardSQLiteOpenHelper.DATABASE_NAME);
    }

    // NOT A SINGLETON, in order to not maintain a connection to the database always
    @Provides
    public FavoriteMovieDAO provideFavoriteMovieDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        return new CupboardFavoriteMovieDAO(sqLiteOpenHelper.getWritableDatabase());
    }
}
