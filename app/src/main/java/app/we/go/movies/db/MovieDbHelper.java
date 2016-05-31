package app.we.go.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.we.go.movies.db.DatabaseContract.FavoriteMoviesTable;

/**
 * Created by apapad on 2016-01-??.
 */
public class MovieDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;

    static final String DATABASE_NAME = "pop_movies.db";

    public static final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoriteMoviesTable.TABLE_ΝΑΜΕ + " (" +
            FavoriteMoviesTable._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
            FavoriteMoviesTable.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
            FavoriteMoviesTable.COLUMN_POSTER_PATH + " TEXT " +
            ");";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFavoritesTable(db);
    }

    private void createFavoritesTable(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            dropFavoritesTable(db);
            // create favorites tables, which was not available in v1
            createFavoritesTable(db);
        }
    }

    private void dropFavoritesTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + FavoriteMoviesTable.TABLE_ΝΑΜΕ + "'");
    }
}
