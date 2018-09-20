package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.RadioArray;
import com.tgithubc.kumao.bean.RecommendSongArray;
import com.tgithubc.kumao.bean.SongListArray;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by tc :)
 */
public interface KuMaoDataSource {

    Observable<Banner> getBanner(String url, Map<String, String> maps);

    Observable<Billboard> getBillboard(String url, Map<String, String> maps);

    Observable<List<Billboard>> getBillboardList(String url, Map<String, String> maps);

    Observable<Song> getSongInfo(String url, Map<String, String> maps);

    Observable<List<String>> getHotWord(String url);

    Observable<SearchResult> getSearchResult(String url, Map<String, String> maps);

    Observable<List<KeyWord>> getSearchHistory();

    void saveSearchHistory(String keyWord);

    void deleteSearchHistory(KeyWord keyWords);

    void clearSearchHistory();

    Observable<SongListArray> getSongListArrary(String url, Map<String, String> maps);

    Observable<SongList> getSongList(String url, Map<String, String> maps);

    Observable<RecommendSongArray> getRecommendSongArray(String url, Map<String, String> maps);

    Observable<RadioArray> getRadioArray(String url, Map<String, String> maps);
}
