package app.we.go.movies.dependency;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import app.we.go.movies.db.CupboardFavoriteMovieDAO;
import app.we.go.movies.db.CupboardSQLiteOpenHelper;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.db.RxCupboardFavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class DatabaseModule {

    private final Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    public SQLiteOpenHelper provideSqLiteOpenHelper() {
        return new CupboardSQLiteOpenHelper(context, CupboardSQLiteOpenHelper.DATABASE_NAME);
    }

    @Provides
    @Singleton
    public RxCupboardFavoriteMovieDAO provideRxFavoriteMovieDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        return new RxCupboardFavoriteMovieDAO(sqLiteOpenHelper.getWritableDatabase());
    }

    // NOT A SINGLETON, in order to not maintain a connection to the database always
    @Provides
    public FavoriteMovieDAO provideFavoriteMovieDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        return new CupboardFavoriteMovieDAO(sqLiteOpenHelper.getWritableDatabase());
    }
}
