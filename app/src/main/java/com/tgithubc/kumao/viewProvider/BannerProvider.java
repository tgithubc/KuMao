package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.FeaturedData;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.module.WebFragment;
import com.tgithubc.kumao.widget.RecyclerViewBanner;

import java.util.List;

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
        return R.layout.rv_item_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, FeaturedData data, int pos) {
        if (data.getData() instanceof BannerResult) {
            List<Banner> bannerList = ((BannerResult) data.getData()).getBannerList();
            RecyclerViewBanner bannerView = helper.getView(R.id.banner_view);
            bannerView.setBannerData(bannerList);
            bannerView.setItemChangeListener((position, imageView) ->
                    ImageLoaderWrapper.getInstance().load(
                            (SimpleDraweeView) imageView,
                            bannerList.get(position).getBannerPic()));
            bannerView.setOnItemClickListener(position -> {
                WebFragment fragment = WebFragment.newInstance(bannerList.get(position).getUrl());
                FragmentOperation.getInstance().showFullFragment(fragment);
            });
        }
    }

}
