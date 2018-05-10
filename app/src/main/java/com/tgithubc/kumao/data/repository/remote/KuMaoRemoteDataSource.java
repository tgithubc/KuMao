package com.tgithubc.kumao.data.repository.remote;

import com.tgithubc.kumao.api.Api;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.http.RetrofitManager;
import com.tgithubc.kumao.util.GsonHelper;

import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by tc :)
 */
public class KuMaoRemoteDataSource implements KuMaoDataSource {

    private static KuMaoRemoteDataSource INSTANCE;

    private KuMaoRemoteDataSource() {
    }

    public static KuMaoRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KuMaoRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<BannerResult> getBanner(String url, Map<String, String> maps) {
        return RetrofitManager.getInstance()
                .createService(Api.class)
                .get(url, maps).flatMap(new Func1<String, Observable<BannerResult>>() {
                    @Override
                    public Observable<BannerResult> call(String baseResponse) {
                        return Observable.just(GsonHelper.getInstance().fromJson(baseResponse, BannerResult.class));
                    }
                });
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return RetrofitManager.getInstance()
                .createService(Api.class)
                .get(url, maps).flatMap(new Func1<String, Observable<Billboard>>() {
                    @Override
                    public Observable<Billboard> call(String baseResponse) {
                        return Observable.just(GsonHelper.getInstance().fromJson(baseResponse, Billboard.class));
                    }
                });
    }
}