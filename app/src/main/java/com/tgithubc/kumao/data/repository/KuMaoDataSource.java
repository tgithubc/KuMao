package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public interface KuMaoDataSource {

    Observable<BannerResult> getBanner(String url, Map<String,String> maps);

    Observable<Billboard> getBillboard(String url, Map<String,String> maps);
}
