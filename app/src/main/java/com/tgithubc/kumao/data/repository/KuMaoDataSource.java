package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;

import rx.Observable;

/**
 * Created by tc :)
 */
public interface KuMaoDataSource {

    Observable<BannerResult> getBanner(int size);

    Observable<Billboard> getBillboard(int type, int offset, int size);
}
