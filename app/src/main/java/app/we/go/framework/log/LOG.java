package app.we.go.framework.log;

import android.util.Log;

/**
 * Alternative logging class that supports building messages using formatted strings
 * and vararg parameters.
 * Note that in the method versions that accept a {@link Throwable}, that parameter
 * has been moved before the message parameter compared to the original
 * {@link Log} methods (for overloading reasons).
 * <p>
 * Created by apapad on 2/04/16.
 */
public class LOG {

    private static boolean enabled;

    public static void enable() {
        enabled = true;
    }

    public static void v(String tag, String msg, Object... params) {
        if (enabled) {
            Log.v(tag, String.format(msg, params));
        }
    }

    public static void v(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.v(tag, String.format(msg, params), tr);
        }
    }

    public static void d(String tag, String msg, Object... params) {
        if (enabled) {
            Log.d(tag, String.format(msg, params));
        }
    }

    public static void d(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.d(tag, String.format(msg, params), tr);
        }
    }

    public static void i(String tag, String msg, Object... params) {
        if (enabled) {
            Log.i(tag, String.format(msg, params));
        }
    }

    public static void i(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.i(tag, String.format(msg, params), tr);
        }
    }

    public static void w(String tag, String msg, Object... params) {
        if (enabled) {
            Log.w(tag, String.format(msg, params));
        }
    }

    public static void w(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.w(tag, String.format(msg, params), tr);
        }
    }

    public static void e(String tag, String msg, Object... params) {
        if (enabled) {
            Log.e(tag, String.format(msg, params));
        }
    }

    public static void e(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.e(tag, String.format(msg, params), tr);
        }
    }

    public static void wtf(String tag, String msg, Object... params) {
        if (enabled) {
            Log.wtf(tag, String.format(msg, params));
        }
    }

    public static void wtf(String tag, Throwable tr, String msg, Object... params) {
        if (enabled) {
            Log.wtf(tag, String.format(msg, params), tr);
        }
    }

}
