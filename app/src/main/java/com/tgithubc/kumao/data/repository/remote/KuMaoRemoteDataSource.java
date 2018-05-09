package com.tgithubc.kumao.data.repository.remote;

import com.tgithubc.kumao.api.MusicApi;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.http.RetrofitManager;

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
    public Observable<BannerResult> getBanner(int numb) {
        return RetrofitManager.getInstance()
                .createService(MusicApi.class)
                .getBanner(numb);
    }

    @Override
    public Observable<Billboard> getBillboard(int type, int offset, int size) {
        return RetrofitManager.getInstance()
                .createService(MusicApi.class)
                .getBillboard(type, offset, size);
    }
}