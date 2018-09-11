package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.HotSongListArrary;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetHostSongListArraryTask extends Task<Task.CommonRequestValue, GetHostSongListArraryTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getHotSongListArrary(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    result.setType(BaseData.TYPE_HOT_SONG_LIST);
                    return new ResponseValue(result);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private HotSongListArrary result;

        public ResponseValue(@NonNull HotSongListArrary result) {
            this.result = result;
        }

        public HotSongListArrary getResult() {
            return result;
        }
    }
}
