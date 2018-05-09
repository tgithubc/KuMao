package com.tgithubc.kumao.module.featured;

import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.FeaturedData;

import java.util.List;

/**
 * Created by tc :)
 */
public interface IFeaturedContract {

    interface V extends IStateView {

        void showFeatureView(List<FeaturedData> mFeedData);
    }

    interface P {

        void getFeaturedData();
    }
}
