package com.tgithubc.kumao.widget.banner;

import android.view.ViewGroup;

/**
 * Created by tc :)
 */
public interface BannerHolderCreator {

    BannerHolder createHolder(ViewGroup parent, int viewType);

    int createItemViewType(int pos);
}
