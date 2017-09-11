package com.p2p.dsad.sixplayler.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用于管理recyclerview间距的类
 * Created by dsad on 2017/9/2.
 */

public class RecyclerViewItemSpace extends RecyclerView.ItemDecoration
{
    private int space;

    public RecyclerViewItemSpace(int space)
    {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
