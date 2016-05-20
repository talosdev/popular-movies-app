package app.we.go.movies.util;

import android.util.Log;

/**
 * Created by apapad on 2/04/16.
 */
public class LOG  {


    public static void v(String tag, String msg, Object... params) {
        Log.v(tag, String.format(msg, params));
    }

    public static void v(String tag, Throwable tr, String msg, Object... params) {
        Log.v(tag, String.format(msg, params), tr);
    }

    public static void d(String tag, String msg, Object... params) {
        Log.d(tag, String.format(msg, params));
    }

    public static void d(String tag, Throwable tr, String msg, Object... params) {
        Log.d(tag, String.format(msg, params), tr);
    }

    public static void i(String tag, String msg, Object... params) {
        Log.i(tag, String.format(msg, params));
    }

    public static void i(String tag, Throwable tr, String msg, Object... params) {
        Log.i(tag, String.format(msg, params), tr);
    }

    public static void w(String tag, String msg, Object... params) {
        Log.w(tag, String.format(msg, params));
    }

    public static void w(String tag, Throwable tr, String msg, Object... params) {
        Log.w(tag, String.format(msg, params), tr);
    }

    public static void e(String tag, String msg, Object... params) {
        Log.e(tag, String.format(msg, params));
    }

    public static void e(String tag, Throwable tr, String msg, Object... params) {
        Log.e(tag, String.format(msg, params), tr);
    }

    public static void wtf(String tag, String msg, Object... params) {
        Log.wtf(tag, String.format(msg, params));
    }

    public static void wtf(String tag, Throwable tr, String msg, Object... params) {
        Log.wtf(tag, String.format(msg, params), tr);
    }

}
