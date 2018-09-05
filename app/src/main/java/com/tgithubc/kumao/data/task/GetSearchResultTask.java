package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Album;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Song;
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
                        BaseData<Artist> artist = new BaseData<>();
                        artist.setType(BaseData.TYPE_SEARCH_RESULT_ARTIST);
                        artist.setData(result.getArtist());
                        list.add(artist);
                    }
                    if (result.isAlbum()) {
                        BaseData<Album> album = new BaseData<>();
                        album.setType(BaseData.TYPE_SEARCH_RESULT_ALBUM);
                        album.setData(result.getAlbum());
                        list.add(album);
                    }
                    List<Song> songList = result.getSongList();
                    if (songList != null && !songList.isEmpty()) {
                        for (Song s : songList) {
                            BaseData<Song> song = new BaseData<>();
                            song.setType(BaseData.TYPE_SEARCH_RESULT_SONG);
                            song.setData(s);
                            list.add(song);
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
