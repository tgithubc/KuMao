package com.tgithubc.kumao.module.featured;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.viewProvider.BannerProvider;
import com.tgithubc.kumao.viewProvider.HotSongListProvider;
import com.tgithubc.kumao.viewProvider.TitleMoreProvider;

import java.util.List;

/**
 * Created by tc :)
 */
public class FeaturedAdapter extends MultipleItemRvAdapter<BaseData, BaseViewHolder> {

    public FeaturedAdapter(@Nullable List<BaseData> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(BaseData baseData) {
        return baseData.getType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new TitleMoreProvider());
        mProviderDelegate.registerProvider(new BannerProvider());
        mProviderDelegate.registerProvider(new HotSongListProvider());
    }
}


