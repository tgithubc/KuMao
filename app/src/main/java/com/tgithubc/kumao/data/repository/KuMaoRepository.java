package com.tgithubc.kumao.data.repository;


import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;

import java.util.List;
import java.util.Map;

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
    public Observable<List<Banner>> getBanner(String url, Map<String, String> maps) {
        return mRemoteDataSource.getBanner(url, maps);
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return mRemoteDataSource.getBillboard(url, maps);
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return mRemoteDataSource.getHotWord(url);
    }
}
