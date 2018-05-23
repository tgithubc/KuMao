package com.tgithubc.kumao.module.search;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.viewProvider.SearchResultAlbumProvider;
import com.tgithubc.kumao.viewProvider.SearchResultArtistProvider;
import com.tgithubc.kumao.viewProvider.SearchResultSongProvider;

import java.util.List;

/**
 * Created by tc :)
 */
public class SearchResultAdapter extends MultipleItemRvAdapter<BaseData, BaseViewHolder> {

    private Context mContext;

    public SearchResultAdapter(Context context, @Nullable List<BaseData> data) {
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
        mProviderDelegate.registerProvider(new SearchResultArtistProvider(mContext));
        mProviderDelegate.registerProvider(new SearchResultSongProvider());
        mProviderDelegate.registerProvider(new SearchResultAlbumProvider());
    }
}


