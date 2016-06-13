package app.we.go.framework.util;

import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class RxUtils {

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && ! subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
