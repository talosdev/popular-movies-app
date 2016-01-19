package com.talosdev.movies.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

/**
 * Created by apapad on 2016-01-15.
 */
public class ContextBasedTest {

    protected static Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
