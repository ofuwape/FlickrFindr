package com.example.tfuwape.flickrfindr.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tfuwape.flickrfindr.R;


/**
 * Line separato
 */
public class LineItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;

    private int mOffset;

    public LineItemDecoration(Drawable divider) {
        super();
        mDivider = divider;
    }

    /**
     * Sets the offset from which the lines start drawing between the items in the list view.
     *
     * @param offset Offset from which to start drawing lines from
     */
    public void setOffset(int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (mDivider == null) {
            super.onDrawOver(c, parent, state);
            return;
        }

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = mOffset; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            final int size = (int) child.getContext().getResources()
                    .getDimension(R.dimen.list_separator_height);
            final int top = child.getTop() - params.topMargin;
            final int bottom = top + size;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}