package com.tgithubc.kumao.data.repository;


import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.SongListArray;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;

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
    public Observable<Banner> getBanner(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getBanner(url, maps), mRemoteDataSource.getBanner(url, maps))
                .first();
    }

    @Override
    public Observable<Billboard> getBillboard(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getBillboard(url, maps), mRemoteDataSource.getBillboard(url, maps))
                .first();
    }

    @Override
    public Observable<List<Billboard>> getBillboardList(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getBillboardList(url, maps), mRemoteDataSource.getBillboardList(url, maps))
                .first();
    }

    @Override
    public Observable<Song> getSongInfo(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getSongInfo(url, maps), mRemoteDataSource.getSongInfo(url, maps))
                .first();
    }

    @Override
    public Observable<List<String>> getHotWord(String url) {
        return Observable
                .concat(mLocalDataSource.getHotWord(url), mRemoteDataSource.getHotWord(url))
                .first();
    }

    @Override
    public Observable<SongListArray> getSongListArrary(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getSongListArrary(url, maps), mRemoteDataSource.getSongListArrary(url, maps))
                .first();
    }

    @Override
    public Observable<SongList> getSongList(String url, Map<String, String> maps) {
        return Observable
                .concat(mLocalDataSource.getSongList(url, maps), mRemoteDataSource.getSongList(url, maps))
                .first();
    }

    @Override
    public Observable<SearchResult> getSearchResult(String url, Map<String, String> maps) {
        return mRemoteDataSource.getSearchResult(url, maps);
    }

    @Override
    public Observable<List<KeyWord>> getSearchHistory() {
        return mLocalDataSource.getSearchHistory();
    }

    @Override
    public void saveSearchHistory(String keyWord) {
        mLocalDataSource.saveSearchHistory(keyWord);
    }

    @Override
    public void deleteSearchHistory(KeyWord keyWord) {
        mLocalDataSource.deleteSearchHistory(keyWord);
    }

    @Override
    public void clearSearchHistory() {
        mLocalDataSource.clearSearchHistory();
    }
}
