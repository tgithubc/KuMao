package com.tgithubc.kumao.data.repository.remote;


import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.http.RetrofitManager;
import com.tgithubc.kumao.parser.ParserFactory;
import com.tgithubc.kumao.util.ACache;
import com.tgithubc.kumao.util.RxHandler;

import java.util.List;
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
    public Observable<List<Banner>> getBanner(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BANNER, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BILLBOARD, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return createObservable(url, null, ParserFactory.PARSE_BILLBOARD, ACache.TIME_DAY);
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        return RetrofitManager.getInstance()
                .executeGet(url, maps)
                .compose(RxHandler.handlerResult(ParserFactory.PARSE_SEARCH_RESULT));
    }

    private <T> Observable<T> createObservable(String url,
                                               Map<String, String> maps,
                                               int type,
                                               int cacheTime) {
        return RetrofitManager.getInstance()
                .executeGet(url, maps)
                .doOnNext(data -> ACache.get(KuMao.getContext()).put(url, data, cacheTime))
                .compose(RxHandler.handlerResult(type));
    }
}