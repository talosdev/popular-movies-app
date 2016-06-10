package app.we.go.movies.dependency;

import android.content.Context;
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
    public SQLiteOpenHelper provideSqLiteOpenHelper() {
        return new CupboardSQLiteOpenHelper(context, CupboardSQLiteOpenHelper.DATABASE_NAME);
    }

    @Provides
    @Singleton
    public RxFavoriteMovieDAO provideRxFavoriteMovieDAO(SQLiteOpenHelper sqLiteOpenHelper,
                                                        @Named("observeOn") Scheduler observeOnScheduler,
                                                        @Named("subscribeOn")Scheduler subscribeOnScheduler) {
        return new RxCupboardFavoriteMovieDAO(sqLiteOpenHelper.getWritableDatabase(),
                observeOnScheduler,
                subscribeOnScheduler);
    }

}
