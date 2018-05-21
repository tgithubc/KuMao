package com.tgithubc.kumao.module.search;

import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.SearchResult;

import java.util.List;

/**
 * Created by tc :)
 */
public interface ISearchContract {

    interface V extends IStateView {

        void showHotWord(List<String> hotword);

        void showHotWordErrorTip();

        void showSearchResult(List<BaseData> result);
    }

    interface P {

        void getHotWord();

        void search(String keyword);

        void searchLoadMore(String keyword, int page);
    }
}
