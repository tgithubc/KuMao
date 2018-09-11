package com.tgithubc.kumao.data.repository.remote;


import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.HotSongListArrary;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;
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
    public Observable<Banner> getBanner(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BANNER, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BILLBOARD, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<List<Billboard>> getBillboardList(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BILLBOARD_LIST, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<Song> getSongInfo(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_SONG_INFO, ACache.TIME_DAY * 30);
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return createObservable(url, null, ParserFactory.PARSE_HOTWORD, ACache.TIME_DAY);
    }

    @Override
    public Observable<HotSongListArrary> getHotSongListArrary(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_HOT_SONG_LIST_ARRARY, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<SongList> getSongList(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_SONG_LIST, 12 * ACache.TIME_HOUR);
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        // not cache
        return RetrofitManager.getInstance()
                .executeGet(url, maps)
                .compose(RxHandler.handlerResult(ParserFactory.PARSE_SEARCH_RESULT));
    }

    @Override
    public Observable<List<KeyWord>> getSearchHistory() {
        return null;
    }

    @Override
    public void saveSearchHistory(String keyWord) {
        // remote do noting
    }

    @Override
    public void deleteSearchHistory(KeyWord keyWords) {
        // remote do noting
    }

    @Override
    public void clearSearchHistory() {
        // remote do noting
    }

    private <T> Observable<T> createObservable(String url,
                                               Map<String, String> maps,
                                               int type,
                                               int cacheTime) {
        return RetrofitManager.getInstance()
                .executeGet(url, maps)
                .doOnNext(data -> {
                    String cacheKey = maps == null ? url : url + maps.toString();
                    ACache.get(KuMao.getContext()).put(cacheKey, data, cacheTime);
                })
                .compose(RxHandler.handlerResult(type));
    }
}