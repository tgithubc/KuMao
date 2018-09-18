package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;


/**
 * Created by tc :)
 */
public class GetSongListTask extends Task<Task.CommonRequestValue, GetSongListTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSongList(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private SongList songList;

        public ResponseValue(@NonNull SongList billboard) {
            this.songList = billboard;
        }

        public SongList getResult() {
            return songList;
        }
    }
}
