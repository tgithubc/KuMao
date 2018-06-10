package com.tgithubc.kumao.data.repository.local;

import android.text.TextUtils;

import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.data.repository.KuMaoDataSource;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.parser.ParserFactory;
import com.tgithubc.kumao.util.ACache;
import com.tgithubc.kumao.util.RxHandler;

import org.greendao.autogen.KeyWordDao;
import org.greendao.autogen.SongDao;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class KuMaoLocalDataSource implements KuMaoDataSource {

    private static KuMaoLocalDataSource INSTANCE;
    private KeyWordDao mKeyWordDao;
    private SongDao mSongDao;

    private KuMaoLocalDataSource() {
        mKeyWordDao = DbCore.getInstance().getDaoSession().getKeyWordDao();
        mSongDao = DbCore.getInstance().getDaoSession().getSongDao();
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
    public Observable<Song> getSongInfo(String url, Map<String, String> maps) {
        String id = maps.get("songid");
        return Observable.create(subscriber -> {
            Song song = mSongDao.queryBuilder().where(SongDao.Properties.SongId.eq(id)).unique();
            if (song != null) {
                // 本地数据库有
                subscriber.onNext(song);
            }
            // 本地数据库无发射onComplete
            // concat只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return createObservable(url, ParserFactory.PARSE_HOTWORD);
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        return null;
    }

    @Override
    public Observable<List<KeyWord>> getSearchHistory() {
        return Observable.create(subscriber -> {
            List<KeyWord> historyList = mKeyWordDao
                    .queryBuilder()
                    .orderDesc(KeyWordDao.Properties.SearchTime)
                    .build()
                    .list();
            subscriber.onNext(historyList);
            subscriber.onCompleted();
        });
    }

    @Override
    public void saveSearchHistory(String keyWord) {
        KeyWord query = mKeyWordDao
                .queryBuilder()
                .where(KeyWordDao.Properties.KeyWord.eq(keyWord))
                .build()
                .unique();
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

    private <T> Observable<T> createObservable(String url, int type) {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            String cache = ACache.get(KuMao.getContext()).getAsString(url);
            if (!TextUtils.isEmpty(cache)) {
                subscriber.onNext(cache);
            }
            subscriber.onCompleted();
        }).compose(RxHandler.handlerResult(type));
    }
}
