package com.tgithubc.kumao.widget.banner;

import android.support.v7.widget.RecyclerView;

/**
 * Created by tc :)
 */
public interface OnBannerScrollListener {

    void onScrollStateChanged(RecyclerView recyclerView, int newState, int pos);

    void onScrolled(RecyclerView recyclerView, int dx, int dy);
}