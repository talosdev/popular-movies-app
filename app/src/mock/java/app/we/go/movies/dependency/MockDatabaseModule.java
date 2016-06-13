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
 * When overriding Dagger2 modules, don't use the @Module, @Provides and scope annotations
 * https://artemzin.com/blog/jfyi-overriding-module-classes-with-dagger2/
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockDatabaseModule {

    private Context context;

    public MockDatabaseModule(Context context) {
        this.context = context;
    }



    @Provides
    @Singleton
    @Named("database")
    public String provideDatabaseName() {
        return CupboardSQLiteOpenHelper.DATABASE_NAME + ".test";
    }

    @Provides
    @Singleton
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
