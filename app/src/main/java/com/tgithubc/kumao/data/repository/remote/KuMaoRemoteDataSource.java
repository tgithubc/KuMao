package com.tgithubc.kumao.data.repository.remote;

import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.http.RetrofitManager;
import com.tgithubc.kumao.util.RxHandler;

import java.util.Map;

import rx.Observable;

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
                .executeGet(url, maps)
                .compose(RxHandler.handlerResult(BannerResult.class));
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return RetrofitManager.getInstance()
                .executeGet(url, maps)
                .compose(RxHandler.handlerResult(Billboard.class));
    }
}