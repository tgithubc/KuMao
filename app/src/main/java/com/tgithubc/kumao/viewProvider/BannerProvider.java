package com.tgithubc.kumao.viewProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.module.WebFragment;
import com.tgithubc.kumao.widget.banner.BannerHolder;
import com.tgithubc.kumao.widget.banner.BannerHolderCreator;
import com.tgithubc.kumao.widget.banner.BannerRecyclerView;

/**
 * Created by tc :)
 */
public class BannerProvider extends BaseItemProvider<Banner, BaseViewHolder> {

    @Override
    public int viewType() {
        return Constant.UIType.TYPE_BANNER;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, Banner banner, int pos) {
        BannerRecyclerView bannerView = helper.getView(R.id.banner_view);
        bannerView.setBannerList(new BannerHolderCreator() {
            @Override
            public BannerHolder createHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
                return new BannerViewHolder(itemView);
            }

            @Override
            public int createItemViewType(int pos) {
                return 0;
            }
        }, banner.getBannerPicList());
        bannerView.setOnBannerItemClickListener(position -> {
            WebFragment fragment = WebFragment.newInstance(banner.getUrlList().get(position));
            FragmentOperation.getInstance().showFullFragment(fragment);
        });
    }

    private static class BannerViewHolder extends BannerHolder<String> {

        private SimpleDraweeView mBannerPic;

        public BannerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            mBannerPic = itemView.findViewById(R.id.banner_pic);
        }

        @Override
        public void updateView(String pic) {
            ImageLoaderWrapper.getInstance().load(mBannerPic, pic);
        }
    }
}
