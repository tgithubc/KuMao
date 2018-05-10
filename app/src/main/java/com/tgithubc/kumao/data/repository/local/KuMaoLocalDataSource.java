package com.tgithubc.kumao.data.repository.local;

import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class KuMaoLocalDataSource implements KuMaoDataSource {

    private static KuMaoLocalDataSource INSTANCE;

    private KuMaoLocalDataSource() {
    }

    public static KuMaoLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KuMaoLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<BannerResult> getBanner(String url, Map<String, String> maps) {
        return null;
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return null;
    }
}
