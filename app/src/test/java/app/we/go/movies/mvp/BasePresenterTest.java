package app.we.go.movies.mvp;

import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.R;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class BasePresenterTest {
    protected void verifyError(ViewMVP view) {
        verify(view).showError(null, any(String.class), eq(R.string.error_generic), isNull(Throwable.class));
        verifyNoMoreInteractions(view);
    }

    protected void verifyFail(ViewMVP view) {
        verify(view).showError(null, any(String.class), eq(R.string.error_network), any(Throwable.class));
        verifyNoMoreInteractions(view);
    }
}
