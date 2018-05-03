package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.BannerResult;

import rx.Observable;

/**
 * Created by tc :)
 */
public interface KuMaoDataSource {

    Observable<BannerResult> getBanner(int numb);
}
