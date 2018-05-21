package com.tgithubc.kumao.module.featured;

import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.BaseData;

import java.util.List;

/**
 * Created by tc :)
 */
public interface IFeaturedContract {

    interface V extends IStateView {

        void showFeatureView(List<BaseData> mFeedData);
    }

    interface P {

        void getFeaturedData();
    }
}
