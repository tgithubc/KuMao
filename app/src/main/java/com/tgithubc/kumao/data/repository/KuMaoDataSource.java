package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;

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
}
