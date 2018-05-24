package com.tgithubc.kumao.data.repository.local;

import android.text.TextUtils;

import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.parser.ParserFactory;
import com.tgithubc.kumao.util.ACache;
import com.tgithubc.kumao.util.RxHandler;

import java.util.List;
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
    public Observable<List<Banner>> getBanner(String url, Map<String, String> maps) {
        return createObservable(url, ParserFactory.PARSE_BANNER);
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return createObservable(url, ParserFactory.PARSE_BILLBOARD);
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return createObservable(url, ParserFactory.PARSE_HOTWORD);
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        return null;
    }

    private <T> Observable<T> createObservable(String url, int type) {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            String cache = ACache.get(KuMao.getContext()).getAsString(url);
            if (!TextUtils.isEmpty(cache)) {
                subscriber.onNext(cache);
            } else {
                subscriber.onCompleted();
            }
        }).compose(RxHandler.handlerResult(type));
    }
}
