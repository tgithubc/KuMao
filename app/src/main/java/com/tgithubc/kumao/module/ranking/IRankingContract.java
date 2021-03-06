package com.tgithubc.kumao.module.ranking;


import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.Billboard;

import java.util.List;

/**
 * Created by tc :)
 */
public interface IRankingContract {

    interface V extends IStateView {

        /**
         * 展示榜单列表
         */
        void showRankingView(List<Billboard> result);
    }

    interface P {

        /**
         * 加载榜单列表
         */
        void getRankingData();
    }
}
