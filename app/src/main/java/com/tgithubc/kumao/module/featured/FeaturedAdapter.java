package com.tgithubc.kumao.module.featured;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.viewProvider.BannerProvider;
import com.tgithubc.kumao.viewProvider.RadioArray3SProvider;
import com.tgithubc.kumao.viewProvider.RecommendSongArrayProvider;
import com.tgithubc.kumao.viewProvider.SongList3SProvider;
import com.tgithubc.kumao.viewProvider.TitleMoreProvider;

import java.util.List;

/**
 * Created by tc :)
 */
public class FeaturedAdapter extends MultipleItemRvAdapter<BaseData, BaseViewHolder> {

    private Context mContext;

    public FeaturedAdapter(Context context, @Nullable List<BaseData> data) {
        super(data);
        this.mContext = context;
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
        mProviderDelegate.registerProvider(new SongList3SProvider(mContext));
        mProviderDelegate.registerProvider(new RecommendSongArrayProvider(mContext));
        mProviderDelegate.registerProvider(new RadioArray3SProvider(mContext));
    }
}


