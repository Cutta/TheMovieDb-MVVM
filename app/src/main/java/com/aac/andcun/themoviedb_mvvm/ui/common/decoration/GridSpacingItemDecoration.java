package com.aac.andcun.themoviedb_mvvm.ui.common.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by andani on 25.07.2017.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public GridSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount)
                outRect.top = spacing;

            outRect.bottom = spacing;
        } else
            super.getItemOffsets(outRect, view, parent, state);
    }

}