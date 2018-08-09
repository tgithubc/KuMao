package com.tgithubc.kumao.widget.banner;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * Created by tc :)
 */
public class BannerLayoutManager extends LinearLayoutManager {

    //越大越慢
    private float MILLISECONDS_PER_INCH = 0.5f;

    public BannerLayoutManager(Context context) {
        super(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return BannerLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            //表示每一dpi的距离要滚动MILLISECONDS_PER_INCH毫秒
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.density;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}