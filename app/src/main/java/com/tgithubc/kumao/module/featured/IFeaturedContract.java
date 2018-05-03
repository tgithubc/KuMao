package com.tgithubc.kumao.module.featured;

import com.tgithubc.kumao.base.IStateView;

/**
 * Created by tc :)
 */
public interface IFeaturedContract {

    interface V extends IStateView {

    }

    interface P {

        void getFeaturedData();
    }
}
