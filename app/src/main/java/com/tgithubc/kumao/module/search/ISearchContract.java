package com.tgithubc.kumao.module.search;

import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.KeyWord;

import java.util.List;

/**
 * Created by tc :)
 */
public interface ISearchContract {

    interface V extends IStateView {

        /**
         * 展示搜索热词
         */
        void showHotWord(List<String> hotword);

        /**
         * 搜索热词获取失败错误提示
         */
        void showHotWordErrorTip();

        /**
         * 展示搜索结果
         */
        void showSearchResult(List<BaseData> result);

        /**
         * 加载更多错误
         */
        void loadMoreError();

        /**
         * 没有更多数据，加载更多完成
         */
        void loadMoreFinish();

        /**
         * 刷新加载更多数据
         */
        void loadMoreRefresh(List<BaseData> searchResult);

        /**
         * 展示搜索历史
         */
        void showSearchHistory(List<KeyWord> historyList);

        /**
         * 清空搜索历史
         */
        void clearSearchHistory();

        /**
         * 单条删除搜索历史并刷新
         */
        void refreshSearchHistory(int position);
    }

    interface P {

        /**
         * 获取搜索热词
         */
        void getHotWord();

        /**
         * 搜索
         */
        void search(String keyword);

        /**
         * 搜索加载更多
         */
        void searchLoadMore();

        /**
         * 获取搜索历史
         */
        void getSearchHistory();

        /**
         * 清除搜索历史
         */
        void clearSearchHistory();

        /**
         * 从数据库删除单条搜索历史
         *
         * @param keyword
         * @param position
         */
        void deleteSearchHistory(KeyWord keyword, int position);
    }
}
