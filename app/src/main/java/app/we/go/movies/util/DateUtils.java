package app.we.go.movies.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO Move to personal util lib
 * Created by apapad on 2016-01-14.
 */
public class DateUtils {

    public final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy/MM/dd");

    public static String formatDate(Date date) {
        return DEFAULT_SDF.format(date);
    }

    public static String formatDate(Date date, SimpleDateFormat sdf) {
        return sdf.format(date);
    }
}
