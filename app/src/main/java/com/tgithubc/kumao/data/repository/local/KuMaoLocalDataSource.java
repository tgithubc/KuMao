package com.tgithubc.kumao.data.repository.local;

import android.text.TextUtils;

import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.RecommendSongArray;
import com.tgithubc.kumao.bean.SongListArray;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.parser.ParserFactory;
import com.tgithubc.kumao.util.ACache;
import com.tgithubc.kumao.util.RxHandler;

import org.greendao.autogen.KeyWordDao;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class KuMaoLocalDataSource implements KuMaoDataSource {

    private static KuMaoLocalDataSource INSTANCE;
    private KeyWordDao mKeyWordDao;

    private KuMaoLocalDataSource() {
        mKeyWordDao = DbCore.getInstance().getDaoSession().getKeyWordDao();
    }

    public static KuMaoLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KuMaoLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<Banner> getBanner(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BANNER);
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BILLBOARD);
    }

    @Override
    public Observable<List<Billboard>> getBillboardList(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_BILLBOARD_LIST);
    }

    @Override
    public Observable<Song> getSongInfo(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_SONG_INFO);
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return createObservable(url, null, ParserFactory.PARSE_HOTWORD);
    }

    @Override
    public Observable<SongListArray> getSongListArrary(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_SONG_LIST_ARRARY);
    }

    @Override
    public Observable<SongList> getSongList(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_SONG_LIST_INFO);
    }

    @Override
    public Observable<RecommendSongArray> getRecommendSongArray(String url, Map<String, String> maps) {
        return createObservable(url, maps, ParserFactory.PARSE_RECOMMEND_SONG_ARRAY);
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        // local do noting
        return null;
    }

    @Override
    public Observable<List<KeyWord>> getSearchHistory() {
        return Observable.create(subscriber -> {
            List<KeyWord> historyList =
                    mKeyWordDao.queryBuilder().orderDesc(KeyWordDao.Properties.SearchTime).build().list();
            subscriber.onNext(historyList);
            subscriber.onCompleted();
        });
    }

    @Override
    public void saveSearchHistory(String keyWord) {
        KeyWord query =
                mKeyWordDao.queryBuilder().where(KeyWordDao.Properties.KeyWord.eq(keyWord)).build().unique();
        if (query == null) {
            KeyWord entity = new KeyWord();
            entity.setKeyWord(keyWord);
            entity.setSearchTime(System.currentTimeMillis());
            mKeyWordDao.insertOrReplace(entity);
        } else {
            query.setSearchTime(System.currentTimeMillis());
            mKeyWordDao.update(query);
        }
    }

    @Override
    public void deleteSearchHistory(KeyWord keyWords) {
        mKeyWordDao.delete(keyWords);
    }

    @Override
    public void clearSearchHistory() {
        mKeyWordDao.deleteAll();
    }

    private <T> Observable<T> createObservable(String url, Map<String, String> maps, int type) {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            String cacheKey = maps == null ? url : url + maps.toString();
            String cache = ACache.get(KuMao.getContext()).getAsString(cacheKey);
            if (!TextUtils.isEmpty(cache)) {
                subscriber.onNext(cache);
            } else {
                // concat只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable
                subscriber.onCompleted();
            }
        }).compose(RxHandler.handlerResult(type));
    }
}
