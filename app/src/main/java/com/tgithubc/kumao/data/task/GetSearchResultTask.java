package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Album;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetSearchResultTask extends Task<Task.CommonRequestValue, GetSearchResultTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSearchResult(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    List<BaseData> list = new ArrayList<>();
                    if (result.isArtist()) {
                        Artist artist = result.getArtist();
                        artist.setType(Constant.UIType.TYPE_SEARCH_RESULT_ARTIST);
                        list.add(artist);
                    }
                    if (result.isAlbum()) {
                        Album album = result.getAlbum();
                        album.setType(Constant.UIType.TYPE_SEARCH_RESULT_ALBUM);
                        list.add(album);
                    }
                    List<Song> songList = result.getSongList();
                    if (songList != null && !songList.isEmpty()) {
                        for (Song s : songList) {
                            s.setType(Constant.UIType.TYPE_SEARCH_RESULT_SONG);
                            list.add(s);
                        }
                    }
                    return new ResponseValue(list);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<BaseData> result;

        public ResponseValue(@NonNull List<BaseData> result) {
            this.result = result;
        }

        public List<BaseData> getResult() {
            return result;
        }
    }
}
