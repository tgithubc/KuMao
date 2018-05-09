package com.tgithubc.kumao.module.featured;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.FeaturedData;

import java.util.List;

/**
 * Created by tc :)
 */
public class FeaturedAdapter extends MultipleItemRvAdapter<FeaturedData, BaseViewHolder> {

    public FeaturedAdapter(@Nullable List<FeaturedData> data) {
        super(data);
    }

    @Override
    protected int getViewType(FeaturedData featuredData) {
        return featuredData.getType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BillboardProvider());
        mProviderDelegate.registerProvider(new BannerProvider());
    }
}


