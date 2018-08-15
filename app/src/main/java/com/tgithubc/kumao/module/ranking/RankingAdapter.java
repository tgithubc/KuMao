package com.tgithubc.kumao.module.ranking;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.viewProvider.BillboardProvider;
import com.tgithubc.kumao.viewProvider.NewBillboardProvider;

import java.util.List;

/**
 * Created by tc :)
 */
public class RankingAdapter extends MultipleItemRvAdapter<BaseData, BaseViewHolder> {

    public RankingAdapter(@Nullable List<BaseData> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(BaseData baseData) {
        return baseData.getType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new NewBillboardProvider());
        mProviderDelegate.registerProvider(new BillboardProvider());
    }
}


