package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */

public class GetHostSongListTask extends Task<Task.CommonRequestValue, GetHostSongListTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getHotSongList(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    BaseData<List<SongList>> songListData = new BaseData<>();
                    songListData.setType(BaseData.TYPE_HOT_SONG_LIST);
                    songListData.setData(result);
                    return new ResponseValue(songListData);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private BaseData<List<SongList>> result;

        public ResponseValue(@NonNull BaseData<List<SongList>> result) {
            this.result = result;
        }

        public BaseData<List<SongList>> getResult() {
            return result;
        }
    }
}
