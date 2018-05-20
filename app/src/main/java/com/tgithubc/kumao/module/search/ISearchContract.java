package com.tgithubc.kumao.module.search;

import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.FeaturedData;

import java.util.List;

/**
 * Created by tc :)
 */
public interface ISearchContract {

    interface V extends IStateView {

        void showHotWord(List<String> hotword);

        void showHotWordErrorTip();
    }

    interface P {

        void getHotWord();

        void search(String keyword);

        void searchLoadMore(String keyword, int page);
    }
}
