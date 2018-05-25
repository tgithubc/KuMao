package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public interface KuMaoDataSource {

    Observable<List<Banner>> getBanner(String url, Map<String, String> maps);

    Observable<Billboard> getBillboard(String url, Map<String, String> maps);

    Observable<List<String>> getHotWord(String url);

    Observable<SearchResult> getSearchResult(String url, Map<String, String> maps);

    Observable<List<KeyWord>> getSearchHistory();

    void saveSearchHistory(String keyWord);
}
