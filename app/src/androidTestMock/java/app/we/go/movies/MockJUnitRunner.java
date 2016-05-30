package app.we.go.movies;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import app.we.go.movies.dependency.TestMoviesApplication;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MockJUnitRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(
            ClassLoader cl, String className, Context context)
            throws InstantiationException,
            IllegalAccessException,
            ClassNotFoundException {
        return super.newApplication(
                cl, TestMoviesApplication.class.getName(), context);
    }
}