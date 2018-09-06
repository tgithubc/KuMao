package com.tgithubc.kumao.module.ranking;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.viewProvider.BillboardProvider;
import com.tgithubc.kumao.viewProvider.NewBillboardProvider;

import java.util.List;

/**
 * Created by tc :)
 */
public class RankingAdapter extends MultipleItemRvAdapter<Billboard, BaseViewHolder> {

    public RankingAdapter(@Nullable List<Billboard> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Billboard billboard) {
        return billboard.getType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new NewBillboardProvider());
        mProviderDelegate.registerProvider(new BillboardProvider());
    }
}


