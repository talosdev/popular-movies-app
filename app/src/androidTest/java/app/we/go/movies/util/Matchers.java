package app.we.go.movies.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class Matchers {

    /**
     * Checks that
     *
     * Taken from
     * http://stackoverflow.com/a/30361345/5758378
     *
     * @param size
     * @return
     */
    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                return ((ListView) view).getChildCount() == size;
            }



            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }


    /**
     * https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f#.y8tkpf7ix
     */
    public static class DrawableMatcher extends TypeSafeMatcher<View> {

        private final int expectedId;
        String resourceName;

        public DrawableMatcher(int expectedId) {
            super(View.class);
            this.expectedId = expectedId;
        }

        @Override
        protected boolean matchesSafely(View target) {
            Drawable drawable;
            if (target instanceof ImageView) {
                drawable = ((ImageView) target).getDrawable();
            } else if (target instanceof ActionMenuItemView) {
                drawable = ((ActionMenuItemView) target).getItemData().getIcon();
            } else {
                return false;
            }
            if (expectedId < 0){
                return drawable == null;
            }
            Resources resources = target.getContext().getResources();
            Drawable expectedDrawable = resources.getDrawable(expectedId);
            resourceName = resources.getResourceEntryName(expectedId);

            if (expectedDrawable == null) {
                return false;
            }

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap otherBitmap = ((BitmapDrawable) expectedDrawable).getBitmap();
            return bitmap.sameAs(otherBitmap);
        }


        @Override
        public void describeTo(Description description) {
            description.appendText("with drawable from resource id: ");
            description.appendValue(expectedId);
            if (resourceName != null) {
                description.appendText("[");
                description.appendText(resourceName);
                description.appendText("]");
            }
        }
    }
}
