package app.we.go.movies.dependency;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import app.we.go.movies.db.CupboardSQLiteOpenHelper;
import app.we.go.movies.db.RxCupboardFavoriteMovieDAO;
import app.we.go.movies.db.RxFavoriteMovieDAO;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

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
    @Singleton
    @Named("database")
    public String provideDatabaseName() {
        return CupboardSQLiteOpenHelper.DATABASE_NAME;
    }

    @Provides
    public SQLiteOpenHelper provideSqLiteOpenHelper(@Named("database") String database) {
        return new CupboardSQLiteOpenHelper(context, database);
    }


    @Provides
    @Singleton
    public SQLiteDatabase provideSQSqLiteDatabase(SQLiteOpenHelper helper) {
        return helper.getWritableDatabase();
    }

    @Provides
    @Singleton
    public RxFavoriteMovieDAO provideRxFavoriteMovieDAO(SQLiteDatabase database,
                                                        @Named("observeOn") Scheduler observeOnScheduler,
                                                        @Named("subscribeOn")Scheduler subscribeOnScheduler) {
        return new RxCupboardFavoriteMovieDAO(database,
                observeOnScheduler,
                subscribeOnScheduler);
    }

}
