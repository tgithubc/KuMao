package com.tgithubc.kumao.module.featured;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.FeaturedData;

/**
 * Created by tc :)
 */
public class BannerProvider extends BaseItemProvider<FeaturedData, BaseViewHolder> {

    @Override
    public int viewType() {
        return FeaturedData.TYPE_BANNER;
    }

    @Override
    public int layout() {
        return R.layout.item_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, FeaturedData data, int position) {

    }
}
