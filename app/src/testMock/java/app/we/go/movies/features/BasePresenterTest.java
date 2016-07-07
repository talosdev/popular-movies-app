package app.we.go.movies.features;

import android.content.Context;

import org.junit.Before;
import org.mockito.Mockito;

import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.R;
import app.we.go.movies.dependency.ApplicationAndroidModule;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.dependency.DaggerTestApplicationComponent;
import app.we.go.movies.dependency.DatabaseModule;
import app.we.go.movies.dependency.MockServiceModule;
import app.we.go.movies.dependency.TestApplicationComponent;
import app.we.go.movies.dependency.TestSchedulersModule;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class BasePresenterTest {


    protected TestApplicationComponent component;

    @Before
    public void setUp() throws Exception {

        Context context = Mockito.mock(Context.class);

        component = DaggerTestApplicationComponent.builder().
                applicationModule(new ApplicationModule()).
                applicationAndroidModule(new ApplicationAndroidModule(context)).
                schedulersModule(new TestSchedulersModule()).
                serviceModule(new MockServiceModule()).
                databaseModule(new DatabaseModule(context)).
                build();

    }

    protected void verifyError(ViewMVP view) {
        verify(view).showError(any(String.class), eq(R.string.error_generic), isNull(Throwable.class));
        verifyNoMoreInteractions(view);
    }

    protected void verifyFail(ViewMVP view) {
        verify(view).showError(any(String.class), eq(R.string.error_network), any(Throwable.class));
        verifyNoMoreInteractions(view);
    }
}
