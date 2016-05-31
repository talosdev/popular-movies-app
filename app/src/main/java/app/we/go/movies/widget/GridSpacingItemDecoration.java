package app.we.go.movies.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private boolean includeEdge;
    private int spacing = -1;
    private float colWidth = -1f;

    /**
     * In order to avoid the confusion that might be caused by spacing and colWidth being
     * of "castable" types, we won't set them in the constructor, but they will have to be
     * determined (at least one of them) through the setters.
     * @param spanCount
     * @param includeEdge
     */
    public GridSpacingItemDecoration(int spanCount, boolean includeEdge) {
        this.spanCount = spanCount;
        this.includeEdge = includeEdge;
    }


    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setColWidth(float colWidth) {
        this.colWidth = colWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (spacing * colWidth == 1) {
            throw new IllegalArgumentException("At least one of spacing or colWidth must be " +
                    "defined through the setters.");
        }

        if (spacing == -1) {
            spacing =(int) (parent.getWidth() - spanCount * colWidth )/
                            // ------------------------------------------------------------
                                             (spanCount + 1);
        }

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            outRect.top=0;
            outRect.bottom=0;
//            if (position < spanCount) { // top edge
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            if (position >= spanCount) {
//                outRect.top = spacing; // item top
//            }
            outRect.top=0;
            outRect.bottom=0;
        }
    }
}