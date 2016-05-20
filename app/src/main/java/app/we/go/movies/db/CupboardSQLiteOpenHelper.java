package app.we.go.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.we.go.movies.model.FavoriteMovie;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class CupboardSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pop_movies.db";
    private static final int DATABASE_VERSION = 6;

    public static final String OLD_TABLE_ΝΑΜΕ = "favorites";


    static {
        // register our models
        cupboard().register(FavoriteMovie.class);
    }

    public CupboardSQLiteOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 6) {
            dropFavoritesTable(db);
            onCreate(db);
        } else {
            // this will upgrade tables, adding columns and new tables.
            // Note that existing columns will not be converted
            cupboard().withDatabase(db).upgradeTables();
            // do migration work
        }

    }

    private void dropFavoritesTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + OLD_TABLE_ΝΑΜΕ + "'");
    }
}
