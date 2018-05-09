package com.tgithubc.kumao.data.repository;


import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;

import rx.Observable;

/**
 * Created by tc :)
 */
public class KuMaoRepository implements KuMaoDataSource {

    private static KuMaoRepository INSTANCE;
    private KuMaoDataSource mRemoteDataSource;
    private KuMaoDataSource mLocalDataSource;

    private KuMaoRepository(KuMaoDataSource remoteDataSource, KuMaoDataSource localDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static KuMaoRepository getInstance(KuMaoDataSource remoteDataSource, KuMaoDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new KuMaoRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Observable<BannerResult> getBanner(int numb) {
        return mRemoteDataSource.getBanner(numb);
    }

    @Override
    public Observable<Billboard> getBillboard(int type, int offset, int size) {
        return mRemoteDataSource.getBillboard(type, offset, size);
    }
}
