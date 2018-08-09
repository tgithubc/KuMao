package com.tgithubc.kumao.widget.banner;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tc :)
 */
public abstract class BannerHolder<T> extends RecyclerView.ViewHolder {

    public BannerHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public abstract void initView(View itemView);

    public abstract void updateView(T data);
}