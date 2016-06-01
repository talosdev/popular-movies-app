package app.we.go.movies.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import org.junit.BeforeClass;

/**
 * Base class for tests that test the cupboard-based implementation
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class CupboardFavoriteMovieDAOTest extends FavoriteMovieDAOTest {


    @BeforeClass
    public static void setUpClass() throws Exception {
        SQLiteOpenHelper helper = new CupboardSQLiteOpenHelper(InstrumentationRegistry.
                getTargetContext(), TEST_DB);
        db = helper.getWritableDatabase();
        dao = new CupboardFavoriteMovieDAO(db);
    }


}